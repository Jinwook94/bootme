import {
  CourseCount,
  FilterButton,
  FilterSelect,
  PaginationWrapper,
  FooterWrapper,
  Footer,
  SlideWrapper,
  BodyWrapper,
  SideFilterWrapper,
  NoResultsMessage,
  CourseListWrapper,
  BodyWrapper2,
  HomeLayout,
  CourseListMenu,
  MenuLeft,
  MenuRight,
} from './style';

import SlideBanner from '../../components/SlideBanner';
import PaginationBar from '../../components/PaginationBar';
import React, { useState } from 'react';
import CourseCardList from '../../components/CourseCardList';
import usePaging from '../../hooks/usePaging';
import useCourses from '../../hooks/queries/course/useCourses';
import Header from '../../components/@common/Header';
import SideFilter from '../../components/SideFilter';
import { useFilters } from '../../hooks/useFilters';

const Home = () => {
  // Fetching data
  const { data, isLoading, isError } = useCourses({});

  // Filtering
  const { selectedFilters } = useFilters();
  let filteredCourses = data || [];

  const filterCourses = () => {
    filteredCourses = filteredCourses.filter((course: Course) => {
      const categories: string[] = [];
      const stacks: string[] = [];
      const costTypes: string[] = [];
      const tested: string[] = [];
      const costs: string[] = [];
      const periods: string[] = [];

      selectedFilters.forEach(filter => {
        const [property, value] = filter.split(':');
        switch (property) {
          case '개발 분야':
            categories.push(value);
            break;
          case '기술 스택':
            stacks.push(value);
            break;
          case '비용 타입':
            costTypes.push(value);
            break;
          case '코딩 테스트':
            tested.push(value);
            break;
          case '비용':
            costs.push(value);
            break;
          case '수강 기간':
            periods.push(value);
            break;
        }
      });

      return (
        (!categories.length ||
          categories.some(
            category => course.categories.super.includes(category) || course.categories.sub.includes(category)
          )) &&
        (!stacks.length ||
          stacks.some(stack => course.stacks.languages.includes(stack) || course.stacks.frameworks.includes(stack))) &&
        (!costTypes.length || costTypes.some(costType => course.costType === costType)) &&
        (!costs.length || costs.some(cost => course.cost <= parseInt(cost))) &&
        (!periods.length || periods.some(period => Math.floor(course.period / 30) + 1 <= parseInt(period))) &&
        (!tested.length || tested.some(test => (String(course.tested) === 'true' ? '있음' : '없음') === test))
      );
    });
    return filteredCourses;
  };

  if (selectedFilters.length > 0) {
    filterCourses();
  }

  // Pagination
  const [cardsPerPage] = useState(12);
  const length = filteredCourses?.length ?? 10; // 데이터를 받아오지 못한 경우 data.length 를 10으로 설정
  const maxPage = Math.floor(length / cardsPerPage) + 1;
  const { currentPage, handleNumberClick, handleNextClick, handlePrevClick } = usePaging(maxPage);

  const indexOfLastCard = currentPage * cardsPerPage;
  const indexOfFirstCard = indexOfLastCard - cardsPerPage;
  const currentCards = filteredCourses!.slice(indexOfFirstCard, indexOfLastCard);

  if (isLoading) {
    return <p>to do: 로딩중 화면 작성</p>;
  }

  if (isError) {
    return <p>to do: 에러 화면 작성</p>;
  }

  return (
    <>
      <Header />
      <SlideWrapper style={{ marginTop: '1.5rem' }}>
        <SlideBanner />
      </SlideWrapper>
      <HomeLayout>
        <BodyWrapper>
          <BodyWrapper2>
            <SideFilterWrapper>
              <SideFilter />
            </SideFilterWrapper>
            <CourseListWrapper>
              {filteredCourses.length === 0 ? (
                <NoResultsMessage>
                  선택하신 조건에 맞는 코스가 없습니다. <br /> 필터 옵션을 변경해 주세요.
                </NoResultsMessage>
              ) : (
                <>
                  <CourseListMenu>
                    <MenuLeft>
                      <CourseCount>{length}개의 커리큘럼</CourseCount>
                    </MenuLeft>
                    <MenuRight>
                      <FilterButton primary>
                        <span> 검색 필터 </span>
                      </FilterButton>
                      <FilterSelect>
                        <option value={'recent'}> 최신순</option>
                        <option value={'popular'}> 인기순</option>
                        <option value={'popular'}> 응답률순</option>
                      </FilterSelect>
                    </MenuRight>
                  </CourseListMenu>
                  <CourseCardList cards={currentCards} />
                </>
              )}
            </CourseListWrapper>
          </BodyWrapper2>
        </BodyWrapper>
        <PaginationWrapper>
          <PaginationBar
            itemsPerPage={cardsPerPage}
            totalItems={length}
            handleNumberClick={handleNumberClick}
            currentPage={currentPage}
            handlePrevClick={handlePrevClick}
            handleNextClick={handleNextClick}
          />
        </PaginationWrapper>
        <FooterWrapper>
          <Footer style={{ textAlign: 'center' }}> Footer 작성 필요</Footer>
        </FooterWrapper>
      </HomeLayout>
    </>
  );
};

export default Home;
