import {
    List,
    Datagrid,
    TextField,
    UrlField,
    Pagination,

} from 'react-admin';

const CompanyPagination = props => <Pagination rowsPerPageOptions={[10, 25, 50, 100]} {...props} />;

export const CompanyList = (props) => (
    <List {...props} sort={{ field: 'id', order: 'DESC' }} perPage={10} pagination={<CompanyPagination />} >
        <Datagrid rowClick="edit"  >
            <TextField source="id" />
            <TextField source="name" label="회사명" />
            <TextField source="serviceName" label="서비스명" />
            <TextField source="courses" label="개설 코스" />
            <UrlField source="url" label="회사 URL" />
            <UrlField source="serviceUrl" label="서비스 URL" />
            <UrlField source="logoUrl" label="로고 이미지 URL" />

            {/* to-do: target 작동하지 않음 -> react-admin 디스코드 채널에 질문*/}
            {/*<ReferenceManyField label="개설 코스" reference="courses" target="companyId" >*/}
            {/*    <SingleFieldList>*/}
            {/*        <ChipField source="title" />*/}
            {/*    </SingleFieldList>*/}
            {/*</ReferenceManyField>*/}

        </Datagrid>
    </List>
);

export default CompanyList;
