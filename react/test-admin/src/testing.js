import React from "react";
import authProvider from "./authProvider";
import CandidateTesting from "./candidateTesting";




class TestingPane extends React.Component {

    constructor(props) {
        super(props);
        this.state = { auth: null, tests: null, id: null }
    }
    async componentDidMount() {
        const auth = await authProvider.getPermissions();
        const id = await authProvider.getUserInfo()
        this.setState({id})
        if(auth == "recruiters"){

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
        let data;

        if(auth == "recruiters"){
            data = <h1>Hello boss</h1>
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