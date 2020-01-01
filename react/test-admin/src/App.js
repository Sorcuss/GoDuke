import React from 'react';
import { Admin, Resource, EditGuesser } from 'react-admin';
import { CandidatesList } from './recruiters';
import resourceProvider from './resourceProvider';


const App = () => (
<Admin dataProvider={resourceProvider}>
  <Resource name="recruiter" list={CandidatesList} edit={EditGuesser}/>
</Admin>
);

export default App;
