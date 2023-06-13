import React, { useEffect, useState } from 'react';
import CourseCardList from '../../components/CourseCardList';
import { useBookmarks } from '../../hooks/useBookmarks';
import { BodyTitle, BodyWrapper, BodyWrapper2, CourseListWrapper, NoResultsMessage, PaginationWrapper } from './style';
import { Pagination } from 'antd';

const BookmarkPage = () => {
  const {
    currentCourses,
    size,
    courseCount,
    fetchBookmarkCourses,
    fetchBookmarkCourseIds,
    bookmarkedCourseIds,
    setBookmarkedCourseIds,
    setIsBookmarked,
  } = useBookmarks();
  const [currentPage, setCurrentPage] = useState(1);
  const handlePageChange = (page: React.SetStateAction<number>) => {
    setCurrentPage(page);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  useEffect(() => {
    fetchBookmarkCourses(currentPage);
  }, [currentPage]);

  useEffect(() => {
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
  }, [currentPage]);

  return (
    <>
      <BodyWrapper>
        <BodyWrapper2>
          <BodyTitle> 북마크 저장 코스 </BodyTitle>
          <CourseListWrapper>
            {courseCount === 0 ? (
              <NoResultsMessage>북마크 저장한 코스가 없습니다.</NoResultsMessage>
            ) : (
              <CourseCardList courses={currentCourses} bookmarkedCourseIds={bookmarkedCourseIds} displayBookmarked />
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
    </>
  );
};

export default BookmarkPage;
