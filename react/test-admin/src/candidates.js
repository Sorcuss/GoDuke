import React from "react";
import { Create, email, SimpleForm, TextInput, List,Datagrid, TextField, DeleteButton } from 'react-admin';

const validateEmail = email();

export const CandidateCreate = props => (
    <Create {...props}>
        <SimpleForm>
            <TextInput label="Email" source="mail" validate={validateEmail} />
        </SimpleForm>
    </Create>
);

export const CandidatesList = (props) => (
    <List  bulkActionButtons={false} {...props}>
        <Datagrid>
            <TextField source="id" sortable={false}/>
            <TextField source="mail" sortable={false}/>
            <DeleteButton undoable={false}/>
        </Datagrid>
    </List>
);