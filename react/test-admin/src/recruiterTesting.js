import React from "react";
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Switch from '@material-ui/core/Switch';
import axios from 'axios'
import { CSVLink } from "react-csv";
import TextField from '@material-ui/core/TextField';
import CSVReader from 'react-csv-reader'
import authProvider from "./authProvider";
function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <Typography
            component="div"
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && <Box p={3}>{children}</Box>}
        </Typography>
    );
}
function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

export default function RecruiterTesting(props) {
    const [value, setValue] = React.useState(0);
    const [open, setOpen] = React.useState(false);
    const [testValue, setTest] = React.useState({});
    const [testName, setTestName] = React.useState("");
    const [questions, setQuestions] = React.useState([]);
    const [rates, setRate] = React.useState([]);
    const [answer, setAnswer] = React.useState([]);
    const useStyles = makeStyles(theme => ({
        appBar: {
            position: 'relative',
        },
        title: {
            marginLeft: theme.spacing(2),
            flex: 1,
        },
    }));
    const classes = useStyles();


    const handleOpenDialog = async  (answers) => {
        setOpen(true);
         const response = await axios.get("https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/tests/" + answers.test.id, {
             headers: {
                 'Authorization': await authProvider.getHeader()
             }
         });
         setTest(response.data);
         setQuestions(response.data.questions)
         setAnswer(answers.answers)
         setRate(new Array(answers.answers.length).fill(false))
    };

    const handleCloseDialog = () => {
        setOpen(false);
        document.location.href="/";
    };

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const handleSubmit = async (event) => {
        event.preventDefault();
        const answer = props.answers[0];
        const request = {
            id: answer.id,
            test: answer.test,
            candidate: answer.candidate,
            answers: answer.answers,
            rates: rates,
            rated: true
        }
        const response = await axios.put(
            'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/answers',
            request, {
                headers: {
                    'Authorization': await authProvider.getHeader(),
                    'Content-Type': 'application/json'
                }
            }
        )
        document.location.href="/";
    }

    const handleCsvUpload = async (data) => {
        const questions = []
        let en = false
        let pl = false
        data.forEach((x) => {
            if(x[2] == "pl"){
                pl = true
            }else{
                en=true
            }
            if(x[4] != "|") {
                questions.push({
                    type: x[1],
                    language: x[2],
                    question: x[3],
                    options: x[4]
                })
            }else{
                questions.push({
                    type: x[1],
                    language: x[2],
                    question: x[3]
                })
            }
        })
        const languages = []
        if(pl) languages.push("pl")
        if(en) languages.push("en")
        const request = {
            languages: languages,
            testName: testName,
            questions: questions,
            recruiter: props.id.attributes.email
        }
        const response = await axios.post(
            'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/tests',
            request,{
                headers: {
                    'Authorization': await authProvider.getHeader(),
                    'Content-Type': 'application/json'
                }
            }
        )
        alert(response)
        console.log(request)
        document.location.href="/#/tests";
        // console.log(data);
        // console.log(testName);
    }


    return (
        <div className={classes.root}>
            <AppBar position="static">
                <Tabs value={value} onChange={handleChange} aria-label="simple tabs example">
                    <Tab label="New answers" {...a11yProps(0)} />
                    <Tab label="Checked answers" {...a11yProps(1)} />
                    <Tab label="Import/Export CSV" {...a11yProps(2)} />
                </Tabs>
            </AppBar>
            <TabPanel value={value} index={0}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Test name</TableCell>
                            <TableCell>Candidate</TableCell>
                            <TableCell>Check</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.answers.filter((a) => !a.rated).map((a) => {
                            return (
                            <TableRow>
                                <TableCell align="center">{a.test.testName}</TableCell>
                                <TableCell align="center">{a.candidate}</TableCell>
                                <TableCell>
                                    <Button variant="outlined" color="primary" onClick={  () => handleOpenDialog(a)}>
                                        Check
                                    </Button>
                                </TableCell>
                            </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Test name</TableCell>
                            <TableCell>Candidate</TableCell>
                            <TableCell>Rate</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.answers.filter((a) => a.rated).map((a) => {
                            let rate = 0;
                            return (
                                <TableRow>
                                    <TableCell align="center">{a.test.testName}</TableCell>
                                    <TableCell align="center">{a.candidate}</TableCell>
                                    <TableCell>
                                        {a.rates.map((x) => {
                                            if(x){
                                                rate++;
                                            }
                                        })}
                                        <Button>{rate}/{a.rates.length}</Button>
                                    </TableCell>
                                </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
            </TabPanel>
            <TabPanel value={value} index={2}>
                <Card>
                    <CardContent>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell></TableCell>
                                    <TableCell>Import</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow>
                                    <TableCell>
                                        <TextField
                                            label="Test name"
                                            variant="outlined"
                                            onChange={(evt) => setTestName(evt.target.value)}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <CSVReader onFileLoaded={data => handleCsvUpload(data)} />
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </CardContent>
                </Card>

                <Table>
                    <TableBody>
                    {props.tests.map((t) => {
                        const tab = []
                        t.questions.map((x, i) => {
                            const secondTab = []
                            secondTab.push(i)
                            secondTab.push(x.type)
                            secondTab.push(x.language)
                            secondTab.push(x.question)
                            if(x.options && x.options.length > 0){
                                const thirdTab = []
                                x.options.map((x) => {
                                    thirdTab.push(x)
                                })
                                secondTab.push(thirdTab)
                            }else secondTab.push("|");
                            secondTab.push("")
                            tab.push(secondTab)
                        })
                        const data = tab;
                        console.log(data)
                        return(
                            <TableRow>
                                <TableCell>{t.testName}</TableCell>
                                <TableCell>
                                        <CSVLink data={data} separator=";" enclosingCharacter={``} filename={t.testName + ".csv"}>
                                            <Button variant="contained">Export</Button>
                                        </CSVLink>
                                </TableCell>
                            </TableRow>
                        )
                    })}
                    </TableBody>
                </Table>
            </TabPanel>

<Dialog fullScreen open={open} onClose={handleCloseDialog}>
                <AppBar className={classes.appBar}>
                    <Toolbar>
                        <IconButton edge="start" color="inherit" onClick={handleCloseDialog} aria-label="close">
                            <CloseIcon />
                        </IconButton>
                        <Typography variant="h6" className={classes.title}>
                            {testValue.testName}
                        </Typography>
                    </Toolbar>
                </AppBar>
                <div>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Question</TableCell>
                                <TableCell>Answer</TableCell>
                                <TableCell>Check</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {questions.map((t, i) => {
                                if(questions.length / 2 <= i) return
                                return (
                                    <TableRow>
                                        <TableCell>{t.question}</TableCell>
                                        <TableCell>{answer[i]}</TableCell>
                                        <TableCell>
                                            <Switch
                                            onChange={(e) => rates[i] = !rates[i]}
                                            ></Switch>
                                        </TableCell>
                                     </TableRow>
                                )
                            })}

                        </TableBody>
                    </Table>
                    <Button
                        onClick={handleSubmit}
                        variant="contained"
                        type="submit"
                    >Submit</Button>
                </div>
            </Dialog>
        </div>
    )
}