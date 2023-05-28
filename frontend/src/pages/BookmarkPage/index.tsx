import React, { useEffect } from 'react';
import usePaging from '../../hooks/usePaging';
import CourseCardList from '../../components/CourseCardList';
import { useBookmarks } from '../../hooks/useBookmarks';
import { BodyTitle, BodyWrapper, BodyWrapper2, CourseListWrapper, NoResultsMessage, PaginationWrapper } from './style';
import { useRecoilState } from 'recoil';
import { currentPageBookmark, currentView } from '../../recoilState';
import { BOOKMARK } from '../../constants/pages';
import { Pagination } from 'antd';

const BookmarkPage = () => {
  const [, setView] = useRecoilState(currentView);
  const [currentPage] = useRecoilState(currentPageBookmark);
  const { isLoading, currentCourses, size, courseCount } = useBookmarks();
  const { handlePageChange } = usePaging(BOOKMARK);

  useEffect(() => {
    setView(BOOKMARK);
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

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
