import {
  ApplyButton,
  CompanyLogo,
  CourseTitleWrapper,
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
  StyledLink,
  Recommended,
  ItemLabel,
  ItemDetail,
  ItemWrapper,
  ItemList,
  Item,
  ItemIcon,
  ItemTitle,
  BottomButtonWrapper,
} from './style';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import DOMPurify from 'dompurify';
import { Button } from 'antd';
import useCourse from '../../hooks/useCourse';
import DateFormatter from '../../components/CourseCard/dateFormatter';
import { appendUtmParams } from '../../api/fetcher';
import Buttons from './Bottons';
import { CalendarIcon1, CalendarIcon2, HomeIcon } from '../../constants/icons';

const CourseDetailPage = () => {
  const { id } = useParams();
  const { fetchCourse, course } = useCourse();
  const [bookmarkedState, setBookmarkedState] = useState(course?.bookmarked);
  const cleanHTML = DOMPurify.sanitize(course?.detail || '');

  useEffect(() => {
    window.scrollTo(0, 0);
    fetchCourse(id).then(fetchedCourse => {
      setBookmarkedState(fetchedCourse?.bookmarked);
    });
  }, []);

  return (
    <>
      <Wrapper1>
        <CourseDetailLayout>
          <Wrapper>
            <Content>
              <ContentHeader>
                <div style={{ display: 'flex', gap: '1.5rem' }}>
                  <CompanyLogoWrapper>
                    <CompanyLogo src={course?.company.logoUrl} alt={course?.company.name} />
                  </CompanyLogoWrapper>
                  <HeaderDescription>
                    <h2>{course?.title}</h2>
                    <h4>
                      <StyledLink href={appendUtmParams(course?.company.serviceUrl)} target="_blank">
                        {course?.company.serviceName}
                      </StyledLink>
                    </h4>
                  </HeaderDescription>
                </div>
                {course?.recommended === true ? <Recommended /> : null}
              </ContentHeader>
              <MobileButtons>
                <ApplyButton>
                  <StyledLink href={appendUtmParams(course?.url)} target="_blank">
                    <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
                      지원하기
                    </Button>
                  </StyledLink>
                </ApplyButton>
                <ButtonWrapper>
                  <Buttons
                    course={course}
                    bookmarkedState={bookmarkedState || false}
                    setBookmarkedState={setBookmarkedState}
                  />
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
              <CourseDetailImg dangerouslySetInnerHTML={{ __html: cleanHTML }} />
              <BottomButtonWrapper>
                <ApplyButton>
                  <StyledLink href={appendUtmParams(course?.url)} target="_blank">
                    <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
                      자세히 알아보기
                    </Button>
                  </StyledLink>
                </ApplyButton>
              </BottomButtonWrapper>
            </Content>
            <Side>
              <SideContent>
                <SideTop>
                  <CourseTitleWrapper>
                    <h4>{course?.title}</h4>
                  </CourseTitleWrapper>
                  <ButtonWrapper>
                    <Buttons
                      course={course}
                      bookmarkedState={bookmarkedState || false}
                      setBookmarkedState={setBookmarkedState}
                    />
                  </ButtonWrapper>
                </SideTop>
                <SideDescription>
                  <ItemWrapper>
                    <ItemList>
                      <Item>
                        <ItemLabel>
                          <ItemIcon>
                            <CalendarIcon1 />
                          </ItemIcon>
                          <ItemTitle>모집 기간</ItemTitle>
                        </ItemLabel>
                        <ItemDetail>
                          <DateFormatter date={course?.dates.registrationStartDate} korean={true} />
                          {' ~ '}
                          <DateFormatter date={course?.dates.registrationEndDate} korean={true} />
                        </ItemDetail>
                      </Item>
                    </ItemList>
                    <ItemList>
                      <Item>
                        <ItemLabel>
                          <ItemIcon>
                            <CalendarIcon2 />
                          </ItemIcon>
                          <ItemTitle>수강 기간</ItemTitle>
                        </ItemLabel>
                        <ItemDetail>
                          <DateFormatter date={course?.dates.courseStartDate} korean={true} />
                          {' ~ '}
                          <DateFormatter date={course?.dates.courseEndDate} korean={true} />
                        </ItemDetail>
                      </Item>
                    </ItemList>
                    <ItemList>
                      <Item>
                        <ItemLabel>
                          <ItemIcon>
                            <HomeIcon />
                          </ItemIcon>
                          <ItemTitle>수강 장소</ItemTitle>
                        </ItemLabel>
                        <ItemDetail>{course?.online ? '온라인' : course?.location}</ItemDetail>
                      </Item>
                    </ItemList>
                  </ItemWrapper>
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
                  <StyledLink href={appendUtmParams(course?.url)} target="_blank">
                    <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
                      지원하기
                    </Button>
                  </StyledLink>
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
