import React from 'react';
import { Admin, Resource, EditGuesser } from 'react-admin';
import authProvider from "./authProvider";
import resourceProvider from './resourceProvider';
import UserIcon from '@material-ui/icons/People';
import ListIcon from '@material-ui/icons/List';
import {CandidateCreate, CandidatesList} from "./candidates";
import {TestCreate, TestsList} from "./tests";

const App = () => (
    <Admin
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
