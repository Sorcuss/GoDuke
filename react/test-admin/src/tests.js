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
    EditButton,
    Edit

} from "react-admin";
import React, {useCallback} from "react";
import authProvider from "./authProvider";
import { useForm } from 'react-final-form';
import { SaveButton, useCreate, useRedirect, useNotify, required } from 'react-admin';
import {Auth} from "aws-amplify";

export const TestsList = (props) => (
    <List pagination={false} exporter={false} bulkActionButtons={false} {...props}>
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

export const TestCreate = (props) => {
    const [languageValue, setLanguageValue] = React.useState("");
    const [emailValue, setEmailValue] = React.useState("");

    const fetchData = async () => {
        try {
            const response = await authProvider.getUserMail();
            return response;
        } catch (error) {
            throw error;
        }
    };

    React.useEffect(() => {
        fetchData()
            .then(setEmailValue)
            .catch(error => {
                console.warn(JSON.stringify(error, null, 2));
            });
    }, []);
    return (
        <Create {...props} undoable={false} >
            <TabbedForm >
                <FormTab label="Name and primary language">
                    <SelectInput validate={required()} label="Your email" source="recruiter" choices={[
                        { id: emailValue.attributes ? emailValue.attributes.email : null, name: emailValue.attributes ? emailValue.attributes.email : null },
                    ]} />
                    <TextInput validate={required()} lgiabel="Test title" source="testName"/>
                    <SelectArrayInput validate={required()} label="Select languages(first is primary)" source="languages" choices={[
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
                            <SelectInput validate={required()} label="type" source="type" choices={[
                                { id: 'O', name: 'Open' },
                                { id: 'W', name: 'Choice' },
                                { id: 'L', name: 'Number' }
                            ]} />
                            <SelectInput validate={required()} required label="Select language" source="language" choices={[
                                { id: languageValue, name: languageValue },
                            ]} />
                            <TextInput validate={required()} label="Question" source="question" />
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

export const TestUpdate = (props) => {
    return (
        <Edit {...props} undoable={false} >
            <TabbedForm >
                <FormTab label="Name and primary language">
                    <TextInput disabled label="Id" source="id" />
                    <TextInput disabled label="Your email" source="recruiter" />
                    <TextInput label="Test title" source="testName"/>
                    <SelectArrayInput validate={required()} label="Select languages(first is primary)" source="languages" choices={[
                        { id: 'en', name: 'English' },
                        { id: 'pl', name: 'Polish' },
                    ]}
                    />
                </FormTab>
                <FormTab label="Questions">
                    <ArrayInput source="questions">
                        <SimpleFormIterator>
                            <SelectInput validate={required()} label="Type" source="type" choices={[
                                { id: 'O', name: 'Open' },
                                { id: 'W', name: 'Choice' },
                                { id: 'L', name: 'Number' }
                            ]} />
                            <SelectInput validate={required()} label="Language" source="language" choices={[
                                { id: 'en', name: 'English' },
                                { id: 'pl', name: 'Polish' },
                            ]} />
                            <TextInput validate={required()} label="Question" source="question" />
                            <ArrayInput label="Options" source="options">
                                <SimpleFormIterator>
                                    <TextInput label="Option" default="Default Value"/>
                                </SimpleFormIterator>
                            </ArrayInput>
                        </SimpleFormIterator>
                    </ArrayInput>
                </FormTab>
                <FormTab label="Candidates">
                    <ReferenceArrayInput  label="Select candidates" source="candidates" reference="candidates">
                        <SelectArrayInput id="mail" optionText="mail" />
                    </ReferenceArrayInput>
                </FormTab>
            </TabbedForm>
        </Edit>
    )
}