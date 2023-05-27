import {
  ApplyButton,
  CompanyLogo,
  CompanyLogoWrapper,
  Content,
  ContentHeader,
  CourseDetailImg,
  CourseDetailLayout,
  CourseInfo,
  CourseInfoItem,
  CourseInfoWrapper,
  HeaderDescription,
  ItemContent,
  ItemName,
  MobileButtons,
  Side,
  SideContent,
  SideDescription,
  CourseTags,
  SideTop,
  ButtonWrapper,
  Wrapper,
  Wrapper1,
  TagItem,
  TagItemSide,
} from './style';
import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import useCourse from '../../hooks/useCourse';
import { Button } from 'antd';
import DateFormatter from '../../components/CourseCard/dateFormatter';
import { appendUtmParams } from '../../api/fetcher';
import Buttons from './Bottons';

const CourseDetailPage = () => {
  const { id } = useParams();
  const { fetchCourse, course } = useCourse();

  useEffect(() => {
    window.scrollTo(0, 0);
    fetchCourse(id).catch(e => console.error(e));
  }, []);

  return (
    <>
      <Wrapper1>
        <CourseDetailLayout>
          <Wrapper>
            <Content>
              <ContentHeader>
                <CompanyLogoWrapper>
                  <a href={appendUtmParams(course?.company.url)} target="_blank" rel="noreferrer">
                    <CompanyLogo src={course?.company.logoUrl} alt={course?.company.name} />
                  </a>
                </CompanyLogoWrapper>
                <HeaderDescription>
                  <h2>{course?.title}</h2>
                  <h4>
                    <a href={appendUtmParams(course?.company.url)}>{course?.company.name}</a>
                  </h4>
                </HeaderDescription>
              </ContentHeader>
              <MobileButtons>
                <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
                  지원하기
                </Button>
                <ButtonWrapper>
                  <Buttons course={course} />
                </ButtonWrapper>
              </MobileButtons>
              <CourseInfoWrapper>
                <CourseInfo>
                  <CourseInfoItem>
                    <ItemName>개발 분야</ItemName>
                    <ItemContent>
                      {course?.superCategories?.map((tag: string, index: number) => (
                        <TagItem key={index} style={{ backgroundColor: '#e6f7ff', color: '#1c1c1c' }}>
                          {tag}
                        </TagItem>
                      ))}
                      {course?.subCategories?.map((tag: string, index: number) => (
                        <TagItem key={index} style={{ backgroundColor: '#e6f7ff', color: '#1c1c1c' }}>
                          {tag}
                        </TagItem>
                      ))}
                    </ItemContent>
                  </CourseInfoItem>
                  <CourseInfoItem>
                    <ItemName>수강 비용</ItemName>
                    <ItemContent>
                      {course?.free ? (course?.kdt ? '무료 (국비)' : '무료') : course?.cost + '만원'}
                    </ItemContent>
                  </CourseInfoItem>
                  <CourseInfoItem>
                    <ItemName>기술 스택</ItemName>
                    <ItemContent>
                      {course?.languages?.map((tag: string, index: number) => (
                        <TagItem key={index} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
                          {tag}
                        </TagItem>
                      ))}
                      {course?.frameworks?.map((tag: string, index: number) => (
                        <TagItem key={index} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
                          {tag}
                        </TagItem>
                      ))}
                    </ItemContent>
                  </CourseInfoItem>
                  <CourseInfoItem>
                    <ItemName>모집 기간</ItemName>
                    <ItemContent>
                      <DateFormatter date={course?.dates.registrationStartDate} korean={true} />
                      {' ~ '}
                      <DateFormatter date={course?.dates.registrationEndDate} korean={true} />
                    </ItemContent>
                  </CourseInfoItem>
                  <CourseInfoItem>
                    <ItemName>코딩 테스트</ItemName>
                    <ItemContent>{course?.tested ? '있음' : '없음'}</ItemContent>
                  </CourseInfoItem>
                  <CourseInfoItem>
                    <ItemName>수강 기간</ItemName>
                    <ItemContent>
                      <DateFormatter date={course?.dates.courseStartDate} korean={true} />
                      {' ~ '}
                      <DateFormatter date={course?.dates.courseEndDate} korean={true} />
                    </ItemContent>
                  </CourseInfoItem>
                  <CourseInfoItem></CourseInfoItem>
                  <CourseInfoItem>
                    <ItemName>수강 장소</ItemName>
                    <ItemContent>{course?.online ? '온라인' : course?.location}</ItemContent>
                  </CourseInfoItem>
                </CourseInfo>
              </CourseInfoWrapper>
              <CourseDetailImg>상세 이미지</CourseDetailImg>
            </Content>
            <Side>
              <SideContent>
                <SideTop>
                  <CompanyLogoWrapper>
                    <a href={appendUtmParams(course?.company.url)} target="_blank" rel="noreferrer">
                      <CompanyLogo src={course?.company.logoUrl} alt={course?.company.name} />
                    </a>
                  </CompanyLogoWrapper>
                  <ButtonWrapper>
                    <Buttons course={course} />
                  </ButtonWrapper>
                </SideTop>
                <SideDescription>
                  <h4>{course?.title}</h4>
                  <h5>
                    <a href={appendUtmParams(course?.company.url)}>{course?.company.name}</a>
                  </h5>
                </SideDescription>
                <CourseTags>
                  {course?.superCategories?.map((tag: string, index: number) => (
                    <TagItemSide key={index} style={{ backgroundColor: '#e6f7ff', color: '#1c1c1c' }}>
                      {tag}
                    </TagItemSide>
                  ))}
                  {course?.subCategories?.map((tag: string, index: number) => (
                    <TagItemSide key={index} style={{ backgroundColor: '#e6f7ff', color: '#1c1c1c' }}>
                      {tag}
                    </TagItemSide>
                  ))}
                  {course?.languages?.map((tag: string, index: number) => (
                    <TagItemSide key={index} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
                      {tag}
                    </TagItemSide>
                  ))}
                  {course?.frameworks?.map((tag: string, index: number) => (
                    <TagItemSide key={index} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
                      {tag}
                    </TagItemSide>
                  ))}
                </CourseTags>
                <ApplyButton>
                  <a href={appendUtmParams(course?.url)} target="_blank" rel="noreferrer">
                    <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
                      지원하기
                    </Button>
                  </a>
                </ApplyButton>
              </SideContent>
            </Side>
          </Wrapper>
        </CourseDetailLayout>
      </Wrapper1>
    </>
  );
};

export default CourseDetailPage;
