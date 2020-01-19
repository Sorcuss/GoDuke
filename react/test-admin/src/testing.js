import React from "react";
import authProvider from "./authProvider";
import CandidateTesting from "./candidateTesting";
import RecruiterTesting from "./recruiterTesting";
import { makeStyles } from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';
import { Alert, AlertTitle } from '@material-ui/lab';

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        '& > * + *': {
            marginTop: theme.spacing(2),
        },
    },
}));

function LinearIndeterminate() {
    const classes = useStyles();

    return (
        <div className={classes.root}>
            <LinearProgress />
            <LinearProgress color="secondary" />
        </div>
    );
}

class TestingPane extends React.Component {

    constructor(props) {
        super(props);
        this.state = { auth: null, tests: null, id: null, answers: null }
    }
    async componentDidMount() {
        const auth = await authProvider.getPermissions();
        const id = await authProvider.getUserInfo()
        this.setState({id})
        if(auth == "recruiters"){
            const url = 'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/answers/recruiter/' + id.attributes.email;
            await fetch(url, {
                headers: {
                    'Authorization': await authProvider.getHeader()
                }
            })
                .then(response => response.json())
                .then(answers => this.setState({ answers }));
            const url2 = 'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/tests/recruiter/' + id.attributes.email;
            await fetch(url2,{
                headers: {
                    'Authorization': await authProvider.getHeader()
                }
            })
                .then(response => response.json())
                .then(tests => this.setState({ tests }));

        }else if(auth == "candidates"){
            const url = 'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/testscandidate/' + id.username;
            await fetch(url,  {
                headers: {
                    'Authorization': await authProvider.getHeader()
                }
            })
                .then(response => response.json())
                .then(tests => this.setState({ tests }));
        }
        this.setState({ auth });
    }
    render() {
        const auth = this.state.auth;
        const tests = this.state.tests;
        const id = this.state.id;
        const answers = this.state.answers;
        let data;

        if(auth == "recruiters"){
            data = <RecruiterTesting tests={tests} id={id} answers={answers}/>
        }else if(auth == "candidates"){
            data = <CandidateTesting tests={tests} id={id}/>
        }else{
            data = <h1>{auth}</h1>
        }
        return (
            <>
                {this.state.auth ? data : <div>
                    <br />
                    <Alert severity="success">
                    <AlertTitle>Loading</AlertTitle>
                        Thanks for being patient
                </Alert>
                    <LinearIndeterminate/>
                </div>
                    }
           </>
        )
    }
}

export default TestingPane;