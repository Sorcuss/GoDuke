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
import {Auth} from "aws-amplify";

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

export const TestCreate = (props) => {
    const [languageValue, setLanguageValue] = React.useState("");
    const [emailValue, setEmailValue] = React.useState("");
    React.useEffect(async () => {
        setEmailValue(await authProvider.getUserMail())
    }, []);
    return (
        <Create {...props}>
            <TabbedForm toolbar={<PostCreateToolbar />}>
                <FormTab label="Name and primary language">
                    <SelectInput required label="Your email" source="recruiter" choices={[
                        { id: emailValue.attributes ? emailValue.attributes.email : null, name: emailValue.attributes ? emailValue.attributes.email : null },
                    ]} />
                    <TextInput lgiabel="Test title" source="testName"/>
                    <SelectArrayInput label="Select languages(first is primary)" source="languages" choices={[
                        { id: 'en', name: 'English' },
                        { id: 'pl', name: 'Polish' },
                    ]}
                                      onChange={(e) => {
                                          const value = e.target.value;
                                          e.target.value.length > 0 ?  setLanguageValue(value[0]) : setLanguageValue("");
                                          console.log(languageValue)
                                          console.log(e.target.value.length)
                                          console.log(e.target.value[0])
                                      }}
                    />
                </FormTab>
                <FormTab label="Questions">
                    <ArrayInput source="questions">
                        <SimpleFormIterator>
                            <SelectInput label="type" source="type" choices={[
                                { id: 'O', name: 'Open' },
                                { id: 'W', name: 'Choice' },
                                { id: 'L', name: 'Number' }
                            ]} />
                            <SelectInput required label="Select language" source="language" choices={[
                                { id: languageValue, name: languageValue },
                            ]} />
                            <TextInput label="Question" source="question" />
                            <ArrayInput label="Options" source="options">
                                <SimpleFormIterator>
                                    <TextInput label="Option" default="Default Value"/>
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
    )
}