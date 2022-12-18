import * as React from "react";
import {
    BooleanInput,
    DateInput,
    Edit,
    NumberInput,
    RadioButtonGroupInput,
    SelectArrayInput,
    SimpleForm,
    TextInput
} from "react-admin";
import FavoriteIcon from "@mui/icons-material/Favorite";

export const CourseEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" label="코스 타이틀" />
            <TextInput source="name" label="코스 이름" />
            <NumberInput source="generation" label="회차" />
            <TextInput source="url" label="코스 URL" />
            <TextInput source="period" label="수강 기간" />
            <DateInput source="dates.registrationStartDate" label="접수 시작일" />
            <DateInput source="dates.registrationEndDate" label="접수 마감일" />
            <DateInput source="dates.courseStartDate" label="코스 시작일" />
            <DateInput source="dates.courseEndDate" label="코스 종료일" />
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
            <NumberInput source="cost" label="비용" />
            <RadioButtonGroupInput source="costType" choices={[
                { id: '무료', name: '무료' },
                { id: '무료국비', name: '무료국비' },
                { id: '유료', name: '유료' },
            ]} label="비용 타입" />
            <RadioButtonGroupInput source="location" choices={[
                { id: '서울 강남구', name: '서울 강남구' },
                { id: '서울 관악구', name: '서울 관악구' },
                { id: '기타', name: '기타' },
            ]} label="위치" />
            <RadioButtonGroupInput source="onOffline" choices={[
                { id: '온라인', name: '온라인' },
                { id: '오프라인', name: '오프라인' },
                { id: '온오프라인혼합', name: '온오프라인혼합' },
                { id: '이거되나', name: '이거되나' },
            ]} label="온오프라인" />
            <RadioButtonGroupInput source="prerequisites" choices={[
                { id: '노베이스', name: '노베이스' },
                { id: '프로그래밍언어기초', name: '프로그래밍언어기초' },
                { id: '코딩테스트풀이가능', name: '코딩테스트풀이가능' },
            ]} label="선수조건" />
            <BooleanInput source="recommended" label="추천여부" options={{ checkedIcon: <FavoriteIcon /> }} />
            <BooleanInput source="tested" label="코딩테스트여부" />
        </SimpleForm>
    </Edit>
);

export default CourseEdit;
