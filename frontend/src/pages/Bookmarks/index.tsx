import React, { useEffect, useState } from 'react';
import Header from '../../components/Header';
import useCourses from '../../hooks/queries/course/useCourses';
import usePaging from '../../hooks/usePaging';
import CourseCardList from '../../components/CourseCardList';
import PaginationBar from '../../components/PaginationBar';
import { useBookmarks } from '../../hooks/useBookmarks';
import { BodyTitle, BodyWrapper, BodyWrapper2, CourseListWrapper } from './style';

const Bookmarks = () => {
  // Fetching data
  const { data, isLoading, isError } = useCourses({});
  const allCards = data || [];

  // Bookmark
  const { getBookmarkedCourses, bookmarkedCourses, setBookmarkedCourses } = useBookmarks();
  const [bookmarkCount, setBookmarkCount] = useState<number>(0);

  // Pagination
  const [cardsPerPage] = useState(12);
  const maxPage = Math.floor(bookmarkCount / cardsPerPage) + 1;
  const { currentPage, handleNumberClick, handleNextClick, handlePrevClick, getCurrentItems } = usePaging(maxPage);

  const bookmarkCourses = allCards.filter(card => bookmarkedCourses.includes(card.id));
  const currentCards = getCurrentItems(cardsPerPage, bookmarkCourses);

  useEffect(() => {
    getBookmarkedCourses()
      .then(response => {
        setBookmarkCount(response.length);
        setBookmarkedCourses(response);
      })
      .then();
  }, []);

  if (isLoading) {
    return <p>to do: 로딩중 화면 작성</p>;
  }

  if (isError) {
    return <p>to do: 에러 화면 작성</p>;
  }

  return (
    <>
      <Header />
      <BodyWrapper>
        <BodyWrapper2>
          <BodyTitle> 북마크 저장 코스 </BodyTitle>
          <CourseListWrapper>
            <CourseCardList allCards={allCards} currentCards={currentCards} displayBookmarked />
          </CourseListWrapper>
        </BodyWrapper2>
      </BodyWrapper>
      <PaginationBar
        itemsPerPage={cardsPerPage}
        totalItems={bookmarkCount}
        handleNumberClick={handleNumberClick}
        currentPage={currentPage}
        handlePrevClick={handlePrevClick}
        handleNextClick={handleNextClick}
      />
    </>
  );
};

export default Bookmarks;
