import React, { useEffect } from 'react';
import usePaging from '../../hooks/usePaging';
import CourseCardList from '../../components/CourseCardList';
import { useBookmarks } from '../../hooks/useBookmarks';
import { BodyTitle, BodyWrapper, BodyWrapper2, CourseListWrapper, NoResultsMessage, PaginationWrapper } from './style';
import { useRecoilState } from 'recoil';
import { currentPageBookmark } from '../../recoilState';
import { Pagination } from 'antd';
import PATH from '../../constants/path';

const BookmarkPage = () => {
  const [currentPage] = useRecoilState(currentPageBookmark);
  const {
    currentCourses,
    size,
    courseCount,
    fetchBookmarkCourses,
    fetchBookmarkCourseIds,
    setBookmarkedCourseIds,
    setIsBookmarked,
  } = useBookmarks();
  const { handlePageChange } = usePaging(PATH.BOOKMARKS);

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
              <CourseCardList courses={currentCourses} displayBookmarked />
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
