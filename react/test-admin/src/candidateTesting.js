import React from 'react';
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
import ListItemText from '@material-ui/core/ListItemText';
import ListItem from '@material-ui/core/ListItem';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import TextField from '@material-ui/core/TextField';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import axios from 'axios'

import FormControlLabel from '@material-ui/core/FormControlLabel';
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

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}



export default function CandidateTesting(props) {
    const [value, setValue] = React.useState(0);
    const [open, setOpen] = React.useState(false);
    const [testValue, setTest] = React.useState([]);
    const [languageValue, setLanguage] = React.useState([]);
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
    const handleOpenDialog = (test, language) => {
        setOpen(true);
        setTest(test);
        setAnswer(new Array(testValue.length));
        setLanguage(language)
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
            "answers": answer,
            "test" : {
                "id": testValue.id,
                "testName": testValue.testName
            },
            "candidate": props.id.username
        };
        const response = await axios.post(
            'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/answers',
            request,
            { headers: { 'Content-Type': 'application/json' } }
        )
            document.location.href="/";
    }


    return (
        <div className={classes.root}>
            <AppBar position="static">
                <Tabs value={value} onChange={handleChange} aria-label="simple tabs example">
                    <Tab label="Your tests" {...a11yProps(0)} />
                </Tabs>
            </AppBar>
            <TabPanel value={value} index={0}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Test name</TableCell>
                            <TableCell>Recruiter mail</TableCell>
                            <TableCell>Try</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.tests.filter((t) => t.id).map(test =>(
                            <TableRow>
                                <TableCell align="center">{test.testName}</TableCell>
                                <TableCell align="center">{test.recruiter}</TableCell>
                                <TableCell align="center">
                                    {test.languages.map((l) => {
                                        return (
                                            <Box m={2}>
                                                <Button variant="outlined" color="primary" onClick={() => handleOpenDialog(test, l)}>
                                                    {l}
                                                </Button>
                                            </Box>
                                        )
                                    })}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Test name</TableCell>
                            <TableCell>Is rated?</TableCell>
                            <TableCell>Rate</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.tests.filter((t) => !t.id).map(test => {
                            return (
                                <TableRow>
                                    <TableCell>{test.name}</TableCell>
                                    <TableCell>{test.rated ? "Yes" : "No"}</TableCell>
                                    <TableCell>{test.rated ? test.score + "/" + 10 : "N/A"}</TableCell>
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
                <List>
                    <form className={classes.container} onSubmit={handleSubmit}>
                        {open ? testValue.questions.filter((ql) => ql.language == languageValue).map((q, i) => {
                            return (
                                <ListItem button>
                                    <ListItemText primary={q.question} />
                                    {q.type == "O" && (
                                        <TextField
                                            id="answers"
                                            label="Your answer"
                                            style={{ margin: 8 }}
                                            fullWidth
                                            multiline
                                            onChange={(e) => answer[i] = e.target.value}
                                            margin="normal"
                                            InputLabelProps={{
                                                shrink: true,
                                            }}
                                            variant="filled"
                                        />
                                    )}
                                    {q.type == "W" && (
                                            <RadioGroup onChange={(e) => answer[i] = e.target.value}  row>
                                                {q.options.map((w) => {
                                                    return(
                                                    <FormControlLabel value={w} control={<Radio />} label={w} />
                                                    )
                                                })}

                                            </RadioGroup>
                                    )}
                                    {q.type == "L" && (
                                        <TextField
                                            fullWidth
                                            type="number"
                                            onChange={(e) => answer[i] = e.target.value}
                                        />
                                    )}
                                </ListItem>
                            )
                        }) : "null"}
                        <Divider />
                        <Button
                            variant="contained"
                            type="submit"
                        >Submit</Button>
                    </form>
                </List>
            </Dialog>
        </div>
    );
}