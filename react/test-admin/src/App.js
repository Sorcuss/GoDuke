import React from 'react';
import { Admin, Resource, ListGuesser } from 'react-admin';
import { CandidatesList } from './recruiters';
import resourceProvider from './resourceProvider';


const App = () => (
<Admin dataProvider={resourceProvider}>
  <Resource name="users" list={ListGuesser} />
</Admin>
);

export default App;
