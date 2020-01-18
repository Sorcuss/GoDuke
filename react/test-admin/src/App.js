import React from 'react';
import { Admin, Resource, EditGuesser, Login } from 'react-admin';
import authProvider from "./authProvider";
import resourceProvider from './resourceProvider';
import UserIcon from '@material-ui/icons/People';
import ListIcon from '@material-ui/icons/List';
import {CandidateCreate, CandidatesList} from "./candidates";
import {TestCreate, TestsList, TestUpdate} from "./tests";
import TestingPane from "./testing";
import { createMuiTheme } from '@material-ui/core/styles';
import red from '@material-ui/core/colors/red';
import SynonymTooltip from "./synonymTooltip";
import MyLayout from "./Layout";
import LoginPage from "./LoginPage";

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

const appTitleStyles = {
    whiteSpace        : 'nowrap',
    overflow          : 'hidden',
    WebkitTextOverflow: 'ellipsis',
    textOverflow      : 'ellipsis',
    margin            : 0,
    letterSpacing     : 0,
    fontSize          : 24,
    fontWeight        : '400',
    color             : 'rgb(255, 255, 255)',
    height            : 44,
    paddingTop        : 10,
    paddingBottom     : 10,
    WebkitFlex        : '1 1 0%',
    MsFlex            : '1 1 0%',
    flex              : '1 1 0%'
};

const App = () => (
    <div>
        <Admin
            title="GoDuke"
            loginPage={LoginPage}
            layout={MyLayout}
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
                    ?          <Resource name="tests" list={TestsList} create={TestCreate} edit={TestUpdate}  icon={ListIcon}/>
                    : null,permissions.includes('recruiters')
                    ?          <SynonymTooltip/>
                    : null,
                <Resource name="answers"/>]}
        </Admin>

    </div>
);

export default App;
