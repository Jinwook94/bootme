import {
    List,
    Datagrid,
    TextField,
    UrlField,
    Pagination
} from 'react-admin';

const CompanyPagination = props => <Pagination rowsPerPageOptions={[10, 25, 50, 100]} {...props} />;

export const CompanyList = (props) => (
    <List {...props} sort={{ field: 'id', order: 'DESC' }} perPage={10} pagination={<CompanyPagination />} >
        <Datagrid rowClick="edit"  >
            <TextField source="id" />
            <TextField source="name" label="회사명" />
            <UrlField source="url" label="회사 URL" />
            <TextField source="courses" label="개설 코스" />
        </Datagrid>
    </List>
);

export default CompanyList;
