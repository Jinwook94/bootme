import * as React from 'react';
import {
    Create,
    SimpleForm,
    TextInput,
    DateInput,
    required,
    NumberInput,
    RadioButtonGroupInput,
    BooleanInput,
    SelectArrayInput
} from 'react-admin';
import FavoriteIcon from '@mui/icons-material/Favorite';

export const CourseCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="title" label="코스 타이틀" validate={[required()]} />
            <TextInput source="name" label="코스 이름" validate={[required()]} />
            <NumberInput source="generation" label="회차" />
            <TextInput source="url" label="코스 URL" validate={[required()]} />
            <TextInput source="period" label="수강 기간" validate={[required()]} />
            <TextInput source="companyName" label="회사명" validate={[required()]} />
            <DateInput source="dates.registrationStartDate" label="접수 시작일" validate={[required()]} />
            <DateInput source="dates.registrationEndDate" label="접수 마감일" validate={[required()]} />
            <DateInput source="dates.courseStartDate" label="코스 시작일" validate={[required()]} />
            <DateInput source="dates.courseEndDate" label="코스 종료일" validate={[required()]} />
            <SelectArrayInput source="tags" choices={[
                { id: '프론트엔드', name: '프론트엔드' },
                { id: '백엔드', name: '백엔드' },
                { id: 'Java', name: 'Java' },
                { id: 'Spring', name: 'Spring' },
                { id: 'Javascript', name: 'Javascript' },
                { id: 'Nodejs', name: 'Nodejs' },
                { id: 'React', name: 'React' },
                { id: 'Vue', name: 'Vue' },
            ]} label="태그" />
            <NumberInput source="cost" label="비용" validate={[required()]} />
            <RadioButtonGroupInput source="costType" choices={[
                { id: '무료', name: '무료' },
                { id: '무료국비', name: '무료국비' },
                { id: '유료', name: '유료' },
            ]} label="비용 타입" validate={[required()]} />
            <RadioButtonGroupInput source="location" choices={[
                { id: '서울 강남구', name: '서울 강남구' },
                { id: '서울 관악구', name: '서울 관악구' },
                { id: '기타', name: '기타' },
            ]} label="위치" validate={[required()]} />
            <RadioButtonGroupInput source="onOffline" choices={[
                { id: '온라인', name: '온라인' },
                { id: '오프라인', name: '오프라인' },
                { id: '온오프라인혼합', name: '온오프라인혼합' },
            ]} label="온오프라인" validate={[required()]} />
            <RadioButtonGroupInput source="prerequisites" choices={[
                { id: '노베이스', name: '노베이스' },
                { id: '프로그래밍언어기초', name: '프로그래밍언어기초' },
                { id: '코딩테스트풀이가능', name: '코딩테스트풀이가능' },
            ]} label="선수조건" validate={[required()]} />
            <BooleanInput source="recommended" label="추천여부" options={{ checkedIcon: <FavoriteIcon /> }} />
            <BooleanInput source="tested" label="코딩테스트여부" />
        </SimpleForm>
    </Create>
);

export default CourseCreate;
