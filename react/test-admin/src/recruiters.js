import React from 'react';
import { List, Datagrid, TextField,EmailField, EditButton } from 'react-admin';

export const CandidatesList = props => (
    <List {...props}>
        <Datagrid rowClick="edit">
            <TextField source="firstname" />
            <TextField source="lastname" />
            <EmailField source="email" />
            <EditButton />
        </Datagrid>
    </List>
);