import {
  CourseCount,
  FilterButton,
  SortSelect,
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
import React, { useEffect, useState } from 'react';
import CourseCardList from '../../components/CourseCardList';
import usePaging from '../../hooks/usePaging';
import useCourses from '../../hooks/queries/course/useCourses';
import Header from '../../components/Header';
import SideFilter from '../../components/Filters/SideFilter';
import { useFilters } from '../../hooks/useFilters';
import ModalFilter from '../../components/Filters/ModalFilter';
import { Select } from 'antd';
import useSorting from '../../hooks/useSorting';

const Home = () => {
  // Fetching data
  const { data, isLoading, isError } = useCourses({});
  const allCards = data || [];

  // Filtering
  const { selectedFilters, filterCourses, handleModal, filteredLength, handleLength } = useFilters();
  let filteredCourses = data || [];

  if (selectedFilters.length > 0 && data) {
    filteredCourses = filterCourses(data);
  }

  // Sorting
  const { sortOption, sortedCards, handleSorting } = useSorting(filteredCourses);

  // Pagination
  const [cardsPerPage] = useState(12);
  const maxPage = Math.floor(length / cardsPerPage) + 1;
  const { currentPage, setCurrentPage, handleNumberClick, handleNextClick, handlePrevClick, getCurrentItems } =
    usePaging(maxPage);
  const currentCards = getCurrentItems(cardsPerPage, sortedCards);

  useEffect(() => {
    handleLength(filteredCourses.length);
  }, [filteredCourses]);

  useEffect(() => {
    setCurrentPage(1);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  }, [sortOption]);

  if (isLoading) {
    return <p>to do: 로딩중 화면 작성</p>;
  }

  if (isError) {
    return <p>to do: 에러 화면 작성</p>;
  }

  return (
    <>
      <ModalFilter />
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
              <CourseListMenu>
                <MenuLeft>
                  <CourseCount>{filteredLength}개의 커리큘럼</CourseCount>
                </MenuLeft>
                <MenuRight>
                  <FilterButton primary onClick={handleModal}>
                    <span> 검색 필터 </span>
                  </FilterButton>
                  <SortSelect>
                    <Select
                      defaultValue="인기순"
                      style={{ width: 94 }}
                      options={[
                        { value: 'popular', label: '인기순' },
                        { value: 'newest', label: '등록순' },
                        { value: 'bookmark', label: '북마크순' },
                      ]}
                      onSelect={handleSorting}
                    />
                  </SortSelect>
                </MenuRight>
              </CourseListMenu>
              {filteredCourses.length === 0 ? (
                <NoResultsMessage>
                  선택하신 조건에 맞는 코스가 없습니다. <br /> 필터 옵션을 변경해 주세요.
                </NoResultsMessage>
              ) : (
                <CourseCardList allCards={allCards} currentCards={currentCards} />
              )}
            </CourseListWrapper>
          </BodyWrapper2>
        </BodyWrapper>
        <PaginationWrapper>
          <PaginationBar
            itemsPerPage={cardsPerPage}
            totalItems={filteredLength}
            handleNumberClick={handleNumberClick}
            currentPage={currentPage}
            handlePrevClick={handlePrevClick}
            handleNextClick={handleNextClick}
          />
        </PaginationWrapper>
        <FooterWrapper>
          <Footer style={{ textAlign: 'center' }}></Footer>
        </FooterWrapper>
      </HomeLayout>
    </>
  );
};

export default Home;
