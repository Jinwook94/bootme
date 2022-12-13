import * as React from 'react';
import {
    Edit,
    SimpleForm,
    TextInput,
    required
} from 'react-admin';

export const CompanyEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="name" label="회사명" validate={[required()]} />
            <TextInput source="url" label="회사 URL" validate={[required()]} />
        </SimpleForm>
    </Edit>
);

export default CompanyEdit;
