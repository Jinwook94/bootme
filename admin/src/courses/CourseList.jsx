import {
    List,
    Datagrid,
    TextField,
    BooleanField,
    ChipField,
    NumberField,
    UrlField,
    Pagination,
    ReferenceField
} from 'react-admin';

const CoursePagination = props => <Pagination rowsPerPageOptions={[10, 25, 50, 100]} {...props} />;

export const CourseList = (props) => (
    <List {...props} sort={{ field: 'id', order: 'DESC' }} perPage={10} pagination={<CoursePagination />} >
        <Datagrid rowClick="edit" sx={{
            '& .column-title': { backgroundColor: '#fee' },
        }}
        >
            <TextField source="id" />
            <TextField source="title" label="코스 타이틀" />
            <UrlField source="url" label="코스 URL" />
            <ReferenceField source="companyId" reference="companies" label="회사명">
                <TextField source="name" />
            </ReferenceField>
            <TextField source="dates.registrationStartDate" label="등록 시작" />
            <TextField source="dates.registrationEndDate" label="등록 마감" />
            <TextField source="dates.courseStartDate" label="코스 시작" />
            <TextField source="dates.courseEndDate" label="코스 종료" />
            <TextField source="tags" label="태그" />
            <NumberField source="cost" label="비용" />
            <ChipField source="costType" label="비용타입" />
            <ChipField source="location" label="위치" />
            <TextField source="onOffline" label="온오프라인" />
            <ChipField source="prerequisites" label="선수조건" />
            <BooleanField source="recommended" label="추천" />
            <BooleanField source="tested" label="코딩테스트" />
        </Datagrid>
    </List>
);

export default CourseList;
