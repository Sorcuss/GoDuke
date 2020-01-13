import React from 'react';
import { Admin, Resource, EditGuesser } from 'react-admin';
import authProvider from "./authProvider";
import resourceProvider from './resourceProvider';
import UserIcon from '@material-ui/icons/People';
import ListIcon from '@material-ui/icons/List';
import {CandidateCreate, CandidatesList} from "./candidates";
import {TestCreate, TestsList} from "./tests";
import TestingPane from "./testing";
import { createMuiTheme } from '@material-ui/core/styles';
import indigo from '@material-ui/core/colors/indigo';
import blue from '@material-ui/core/colors/blue';
import red from '@material-ui/core/colors/red';

const theme = createMuiTheme({
    palette: {
        secondary: {
            light: '#757ce8',
            main: '#3f50b5',
            dark: '#002884',
            contrastText: '#fff',
        },
        error: red,
        contrastThreshold: 3,
        tonalOffset: 0.8,
    },
});

const App = () => (
    <Admin
        theme={theme}
        dashboard={TestingPane}
        dataProvider={resourceProvider}
        authProvider={authProvider}
    >
        {permissions => [
            permissions.includes('recruiters')
                ?  <Resource name="candidates" list={CandidatesList}  create={CandidateCreate} icon={UserIcon}/>
                : null,
            permissions.includes('recruiters')
                ?  <Resource name="tests" list={TestsList} create={TestCreate} edit={EditGuesser}  icon={ListIcon}/>
                : null,
         <Resource name="answers"/>]}
    </Admin>
);

export default App;
