import {
  CourseListPageLayout,
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
  CourseListMenu,
  MenuLeft,
  MenuRight,
  SearchInput,
  MenuContainer,
  MobileHeader,
  MobileHeaderTextLarge,
  MobileHeaderTextMedium,
} from './style';

import SlideBanner from '../../components/SlideBanner';
import React, { useEffect, useState } from 'react';
import CourseCardList from '../../components/CourseCardList';
import { useCourses } from '../../hooks/useCourses';
import SideFilter from '../../components/Filters/SideFilter';
import { useCourseFilters } from '../../hooks/useFilters';
import ModalFilter from '../../components/Filters/ModalFilter';
import { Pagination, Select, Space } from 'antd';
import Search from 'antd/es/input/Search';
import { useBookmarks } from '../../hooks/useBookmarks';

const CourseListPage = () => {
  const { fetchCourses, courseCount, size, currentCourses, sortOption, handleSorting, onSearch } = useCourses();
  const { selectedFilters, handleModal } = useCourseFilters();
  const { fetchBookmarkCourses, fetchBookmarkCourseIds, bookmarkedCourseIds, setBookmarkedCourseIds, setIsBookmarked } =
    useBookmarks();
  const [currentPage, setCurrentPage] = useState(1);
  const [resetPagination, setResetPagination] = useState(false);

  const handlePageChange = (page: React.SetStateAction<number>) => {
    setCurrentPage(page);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  useEffect(() => {
    if (resetPagination) {
      setCurrentPage(1);
      setResetPagination(false);
    }
    fetchCourses(sortOption, currentPage);
    fetchBookmarkCourses(currentPage);

    const fetchedPromise = fetchBookmarkCourseIds();
    if (fetchedPromise) {
      fetchedPromise.then(response => {
        if (response) {
          setBookmarkedCourseIds(response);
          const updatedIsBookmarked: { [key: string]: boolean } = {};
          response.forEach((courseId: number) => {
            updatedIsBookmarked[courseId] = true;
          });
          setIsBookmarked(updatedIsBookmarked);
        }
      });
    }
  }, [sortOption, selectedFilters, currentPage, resetPagination]);

  useEffect(() => {
    setCurrentPage(1);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  }, [sortOption]);

  return (
    <>
      <ModalFilter />
      <MobileHeader>
        <MobileHeaderTextLarge>부트캠프</MobileHeaderTextLarge>
        <MobileHeaderTextMedium>소프트웨어 커리큘럼을 한 곳에서 비교하세요.</MobileHeaderTextMedium>
      </MobileHeader>
      <SlideWrapper style={{ marginTop: '1.5rem' }}>
        <SlideBanner />
      </SlideWrapper>
      <CourseListPageLayout>
        <BodyWrapper>
          <BodyWrapper2>
            <SideFilterWrapper>
              <SideFilter />
            </SideFilterWrapper>
            <CourseListWrapper>
              <CourseListMenu>
                <MenuContainer>
                  <MenuLeft>
                    <CourseCount>{courseCount}개의 커리큘럼</CourseCount>
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
                </MenuContainer>
                <SearchInput>
                  <Space direction="vertical">
                    <Search placeholder="커리큘럼 검색" onSearch={onSearch} size="large" style={{ width: 350 }} />
                  </Space>
                </SearchInput>
              </CourseListMenu>
              {courseCount === 0 ? (
                <NoResultsMessage>
                  선택하신 조건에 맞는 코스가 없습니다. <br /> 필터 옵션을 변경해 주세요.
                </NoResultsMessage>
              ) : (
                <CourseCardList courses={currentCourses} bookmarkedCourseIds={bookmarkedCourseIds} />
              )}
            </CourseListWrapper>
          </BodyWrapper2>
        </BodyWrapper>
        <PaginationWrapper>
          <Pagination
            current={currentPage}
            pageSize={size}
            total={courseCount}
            onChange={handlePageChange}
            showSizeChanger={false}
          />
        </PaginationWrapper>
        <FooterWrapper>
          <Footer style={{ textAlign: 'center' }}></Footer>
        </FooterWrapper>
      </CourseListPageLayout>
    </>
  );
};

export default CourseListPage;
