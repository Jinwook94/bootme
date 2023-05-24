import CourseCard, { CourseCardProps } from '../CourseCard';
import { CourseCardListStyle } from './style';
import React, { useEffect, useRef } from 'react';
import { useBookmarks } from '../../hooks/useBookmarks';

const CourseCardList = ({ courses, displayBookmarked }: CourseCardListProps) => {
  const isMounted = useRef(false);
  const {
    isBookmarked,
    setIsBookmarked,
    bookmarkedCourseIds,
    setBookmarkedCourseIds,
    isLoading,
    setIsLoading,
    fetchBookmarkCourseIds,
  } = useBookmarks();

  // 1. onMount 시점에 해당 회원이 저장한 북마크 코스 정보를 가져온다 (로그인 된 경우만)
  useEffect(() => {
    const memberId = localStorage.getItem('MemberId');
    if (memberId) {
      fetchBookmarkCourseIds().then(response => {
        setBookmarkedCourseIds(response);
      });
    }
  }, []);

  // 2. 회원이 북마크 저장한 코스의 isBookmarked 값을 true 설정한다.
  useEffect(() => {
    if (isMounted.current) {
      const states = { ...isBookmarked };
      for (const id of courses.map(course => course.id)) {
        states[id] = bookmarkedCourseIds.includes(id);
      }
      setIsBookmarked(states);
    } else {
      isMounted.current = true;
    }
  }, [bookmarkedCourseIds]);

  //3. isBookmarked 값 설정이 완료되면 isLoading 값을 변경하여 CourseCard 랜더링 될 수 있도록 한다.
  useEffect(() => {
    setIsLoading(false);
  }, [isBookmarked]);

  return (
    <CourseCardListStyle>
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        courses
          .filter(course => {
            return displayBookmarked ? isBookmarked[course.id] : true;
          })
          .map(({ id, title, url, company, categories, stacks, dates, period, cost, costType }: CourseCardProps) => (
            <CourseCard
              key={id}
              id={id}
              title={title}
              url={url}
              company={company}
              categories={categories}
              stacks={stacks}
              dates={dates}
              cost={cost}
              costType={costType}
              period={period}
            />
          ))
      )}
    </CourseCardListStyle>
  );
};

interface CourseCardListProps {
  courses: Course[];
  displayBookmarked?: boolean;
}

export default CourseCardList;
