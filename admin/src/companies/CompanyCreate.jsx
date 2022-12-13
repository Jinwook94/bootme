import * as React from 'react';
import {
    Create,
    SimpleForm,
    TextInput,
    required
} from 'react-admin';

export const CompanyCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="name" label="회사명" validate={[required()]} />
            <TextInput source="url" label="회사 URL" validate={[required()]} />
        </SimpleForm>
    </Create>
);

export default CompanyCreate;
