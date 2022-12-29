import {
  Wrapper,
  CourseListHeader,
  HeaderLeft,
  CourseCount,
  HeaderRight,
  FilterButton,
  FilterSelect,
  PaginationWrapper,
  FooterWrapper,
  Footer,
} from './style';

import SlideBanner from '../../components/SlideBanner';
import PaginationBar from '../../components/PaginationBar';
import React, { useState } from 'react';
import CourseCardList from '../../components/CourseCardList';
import usePaging from '../../hooks/usePaging';
import useCourses from '../../hooks/queries/course/useCourses';
import { Layout } from '../../components/@common/Layout';

const Home = () => {
  const { data, isLoading, isError } = useCourses({});

  const [cardsPerPage] = useState(20);
  const length = data?.length ?? 10; // 데이터를 받아오지 못한 경우 data.length 를 10으로 설정
  const maxPage = Math.floor(length / cardsPerPage) + 1;
  const { currentPage, handleNumberClick, handleNextClick, handlePrevClick } = usePaging(maxPage);

  // Get current cards
  const indexOfLastCard = currentPage * cardsPerPage;
  const indexOfFirstCard = indexOfLastCard - cardsPerPage;

  if (isLoading) {
    return <p>to do: 로딩중 화면 작성</p>;
  }

  if (isError) {
    return <p>to do: 에러 화면 작성</p>;
  }

  // 로딩 되기 전에 data.slice() 호출하면 data = undefined 오류 발생 -> 로딩 후에 data.slice() 호출
  const currentCards = data!.slice(indexOfFirstCard, indexOfLastCard);

  return (
    <Layout>
      <Wrapper style={{ marginTop: '32px' }}>
        <SlideBanner />
      </Wrapper>
      <Wrapper>
        <CourseListHeader>
          <HeaderLeft>
            <CourseCount>
              <span> 100개의 커리큘럼 </span>
            </CourseCount>
          </HeaderLeft>
          <HeaderRight>
            <FilterButton primary>
              <span> 검색 필터 </span>
            </FilterButton>
            <FilterSelect>
              <option value={'recent'}> 최신순</option>
              <option value={'popular'}> 인기순</option>
              <option value={'popular'}> 응답률순</option>
            </FilterSelect>
          </HeaderRight>
        </CourseListHeader>
        <CourseCardList cards={currentCards} />
      </Wrapper>
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
    </Layout>
  );
};

export default Home;
