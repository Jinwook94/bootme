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
            <TextInput source="serviceName" label="서비스명" validate={[required()]} />
            <TextInput source="url" label="회사 URL" validate={[required()]} fullWidth />
            <TextInput source="serviceUrl" label="서비스 URL" validate={[required()]} fullWidth />
            <TextInput source="logoUrl" label="로고 이미지 URL" validate={[required()]} fullWidth />
        </SimpleForm>
    </Create>
);

export default CompanyCreate;

