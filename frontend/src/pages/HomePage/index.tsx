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
  SearchInput,
  MenuContainer,
} from './style';

import SlideBanner from '../../components/SlideBanner';
import React, { useEffect } from 'react';
import CourseCardList from '../../components/CourseCardList';
import usePaging from '../../hooks/usePaging';
import { useCourses } from '../../hooks/useCourses';
import SideFilter from '../../components/Filters/SideFilter';
import { useCourseFilters } from '../../hooks/useFilters';
import ModalFilter from '../../components/Filters/ModalFilter';
import { Pagination, Select, Space } from 'antd';
import { useRecoilState } from 'recoil';
import { currentPageHome, currentView } from '../../recoilState';
import { HOME } from '../../constants/pages';
import Search from 'antd/es/input/Search';

const HomePage = () => {
  const [, setView] = useRecoilState(currentView);
  const { handleModal } = useCourseFilters();
  const { courseCount, size, currentCourses, sortOption, handleSorting, onSearch } = useCourses();
  const { handlePageChange } = usePaging(HOME);
  const [currentPage, setCurrentPage] = useRecoilState(currentPageHome);

  useEffect(() => {
    setView(HOME);
  }, []);

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
                <CourseCardList courses={currentCourses} />
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
      </HomeLayout>
    </>
  );
};

export default HomePage;
