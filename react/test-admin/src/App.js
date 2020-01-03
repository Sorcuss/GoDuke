import React from 'react';
import { Admin, Resource } from 'react-admin';
import authProvider from "./authProvider";
import resourceProvider from './resourceProvider';
import UserIcon from '@material-ui/icons/People';
import {CandidateCreate, CandidatesList} from "./candidates";


// const App = () => (
// <Admin authProvider={authProvider} dataProvider={resourceProvider}>
//   <Resource name="users" list={ListGuesser} />
// </Admin>
// );

const App = () => (
    <Admin
        dataProvider={resourceProvider}
        authProvider={authProvider}
    >
        {permissions => [
            permissions.includes('recruiters')
                ?  <Resource name="candidates" list={CandidatesList}  create={CandidateCreate} icon={UserIcon}/>
                : null,
        , <Resource name="answers"/>]}
    </Admin>
);

export default App;
