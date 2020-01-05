import {
    Datagrid,
    DeleteButton,
    List,
    TextField,
    Create,
    TextInput,
    TabbedForm,
    FormTab,
    SelectInput,
    ReferenceArrayInput,
    SelectArrayInput,
    ArrayInput,
    SimpleFormIterator,
    Toolbar,
    EditButton

} from "react-admin";
import React, {useCallback} from "react";
import authProvider from "./authProvider";
import { useForm } from 'react-final-form';
import { SaveButton, useCreate, useRedirect, useNotify } from 'react-admin';

export const TestsList = (props) => (
    <List  bulkActionButtons={false} {...props}>
        <Datagrid>
            <TextField source="id" sortable={false}/>
            <TextField source="testName" sortable={false}/>
            <TextField source="candidates" sortable={false}/>
            <TextField source="languages" sortable={false}/>
            <EditButton/>
            <DeleteButton undoable={false}/>
        </Datagrid>
    </List>
);

const PostCreateToolbar = props => <Toolbar {...props} >
    <SaveButton label="Create" redirect="list" />
</Toolbar>;

export const TestCreate = (props) => (
    <Create {...props}>
        <TabbedForm toolbar={<PostCreateToolbar />}>
            <FormTab label="Name and primary language">
                <TextInput label="Your email" disabled source="recruiter" defaultValue={authProvider.getUserMail()}/>
                <TextInput label="Test title" source="testName"/>
                <SelectArrayInput label="Select languages(first is primary)" source="languages" choices={[
                    { id: 'en', name: 'English' },
                    { id: 'pl', name: 'Polish' },
                ]} />
            </FormTab>
            <FormTab label="Questions">
                <ArrayInput source="questions">
                    <SimpleFormIterator>
                        <SelectInput label="type" source="type" choices={[
                            { id: 'O', name: 'Open' },
                            { id: 'W', name: 'Choice' },
                            { id: 'L', name: 'Number' }
                        ]} />
                        <SelectInput label="Select language" source="language" choices={[
                            { id: 'en', name: 'English' },
                            { id: 'pl', name: 'Polish' },
                        ]} />
                        <TextInput label="Question" source="question" />
                        <ArrayInput label="Options" source="options">
                            <SimpleFormIterator>
                                <TextInput label="Option" source="option" />
                            </SimpleFormIterator>
                        </ArrayInput>
                    </SimpleFormIterator>
                </ArrayInput>
            </FormTab>
            <FormTab label="Candidates">
                <ReferenceArrayInput label="Select candidates" source="candidates" reference="candidates">
                    <SelectArrayInput id="mail" optionText="mail" />
                </ReferenceArrayInput>
            </FormTab>
        </TabbedForm>
    </Create>
);