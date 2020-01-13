import React from "react";
import authProvider from "./authProvider";
import CandidateTesting from "./candidateTesting";
import RecruiterTesting from "./recruiterTesting";




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
            const url = 'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/answers';
            await fetch(url)
                .then(response => response.json())
                .then(answers => this.setState({ answers }));
            const url2 = 'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/tests';
            await fetch(url2)
                .then(response => response.json())
                .then(tests => this.setState({ tests }));

        }else if(auth == "candidates"){
            const url = 'https://xt9q5i3pj9.execute-api.us-east-1.amazonaws.com/goduke-api-1/testscandidate/' + id.username;
            console.log(url)
            await fetch(url)
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
           {data}
           </>
        )
    }
}

export default TestingPane;