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
    const [candidateAnswer, setTestCandidateAnswer] = React.useState({});
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
        root:{
            margin: "10px"
        }
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
         setTestCandidateAnswer(answers);
         if(response.data.languages.length > 1){
             setQuestions(response.data.questions.slice(0, (response.data.questions.length / 2)))
         }else{
             setQuestions(response.data.questions)
         }

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
        const request = {
            id: candidateAnswer.id,
            test: candidateAnswer.test,
            candidate: candidateAnswer.candidate,
            answers: candidateAnswer.answers,
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
    const handleCsvUploadExampleData = async (data) => {
        const questions = []
        const verifiedQuestions = []
        if(data){
            data.forEach((q) => {
                if(q[0]){
                    questions.push(q[0].split(";"));
                }
            })
            for(const q of questions){
                if(!q[3] || q[3] === ""){
                    alert("Error in column 3. Question field cannot be empty.");
                    verifiedQuestions.length = 0;
                    document.location.href="/";
                    break;
                }else if(!q[1] || q[1] === ""){
                    alert("Answer options cannot be empty.");
                    verifiedQuestions.length = 0;
                    document.location.href="/";
                    break;
                }else if((q[1] === "O" || q[1] === "L") && q[4] != "|"){
                    alert("Matching delimeter (|) missing");
                    verifiedQuestions.length = 0;
                    document.location.href="/";
                    break;
                } else if(q[1] === "W" && parseInt(q[4]) <= 0){
                    alert("Number of answer options must be at least one.");
                    verifiedQuestions.length = 0;
                    document.location.href="/";
                    break;
                }else if(!testName || testName === ""){
                    alert("Test name is empty.");
                    verifiedQuestions.length = 0;
                    document.location.href="/";
                    break;
                }else{
                    verifiedQuestions.push(q);
                }
            }
        if(verifiedQuestions.length === 0){
            return ;
        }
            console.log(verifiedQuestions);
        }
        const requestQuestions = []
        let en = false
        let pl = false
        verifiedQuestions.forEach((x) => {
            if(x[2] == "pl"){
                pl = true
            }else{
                en=true
            }
            if(x[4] != "|") {
                const options = x.slice(5, 5 + parseInt(x[4]))
                requestQuestions.push({
                    type: x[1],
                    language: x[2].toLowerCase(),
                    question: x[3],
                    options: options
                })
            }else{
                requestQuestions.push({
                    type: x[1],
                    language: x[2].toLowerCase(),
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
            questions: requestQuestions,
            recruiter: props.id.attributes.email
        };
        const response = await axios.post(
            'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/tests',
            request,{
                headers: {
                    'Authorization': await authProvider.getHeader(),
                    'Content-Type': 'application/json'
                }
            }
        )
        if(response.status === 200){
            alert("Success!");
        }else{
            alert("Unknown error!");
        }
        document.location.href="/#/tests";
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
                                    <TableCell>Import CSV</TableCell>
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
                                        <CSVReader onFileLoaded={data => handleCsvUploadExampleData(data)} />
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </CardContent>
                </Card>

                <Table>
                    <TableBody>
                    {props.tests.map((t) => {
                        const data = []
                        t.questions.map((x, i) => {
                            const q = []
                            q.push("\"" + (i + 1))
                            q.push(x.type)
                            q.push(x.language)
                            q.push(x.question)
                            if(x.options && x.options.length > 0){
                                q.push(x.options.length)
                                q.push(x.options.join(";"))
                            }else q.push("|");
                            q.push("\"")
                            data.push(q)
                        })
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