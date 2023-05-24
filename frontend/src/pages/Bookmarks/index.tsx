import React, { useEffect } from 'react';
import Header from '../../components/Header';
import usePaging from '../../hooks/usePaging';
import CourseCardList from '../../components/CourseCardList';
import PaginationBar from '../../components/PaginationBar';
import { useBookmarks } from '../../hooks/useBookmarks';
import { BodyTitle, BodyWrapper, BodyWrapper2, CourseListWrapper } from './style';
import { useRecoilState } from 'recoil';
import { currentPageBookmark, currentView } from '../../recoilState';
import { BOOKMARK } from '../../constants/pages';

const Bookmarks = () => {
  const [, setView] = useRecoilState(currentView);
  const [currentPage] = useRecoilState(currentPageBookmark);
  const { isLoading, currentCourses, maxPage } = useBookmarks();
  const { handleNumberClick, handleNextClick, handlePrevClick } = usePaging(BOOKMARK, maxPage);

  useEffect(() => {
    setView(BOOKMARK);
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <Header />
      <BodyWrapper>
        <BodyWrapper2>
          <BodyTitle> 북마크 저장 코스 </BodyTitle>
          <CourseListWrapper>
            <CourseCardList courses={currentCourses} displayBookmarked />
          </CourseListWrapper>
        </BodyWrapper2>
      </BodyWrapper>
      <PaginationBar
        maxPage={maxPage}
        handleNumberClick={handleNumberClick}
        currentPage={currentPage}
        handlePrevClick={handlePrevClick}
        handleNextClick={handleNextClick}
      />
    </>
  );
};

export default Bookmarks;
