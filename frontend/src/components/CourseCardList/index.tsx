import CourseCard, { CourseCardProps } from '../CourseCard';
import { CourseCardListStyle } from './style';
import React, { useEffect, useRef } from 'react';
import { useBookmarks } from '../../hooks/useBookmarks';
import { SimpleGrid } from '@mantine/core';

const CourseCardList = ({ courses, displayBookmarked, bookmarkedCourseIds }: CourseCardListProps) => {
  const isMounted = useRef(false);
  const { isBookmarked, setIsBookmarked } = useBookmarks();

  // 회원이 북마크 저장한 코스의 isBookmarked 값을 true 설정한다.
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

  return (
    <CourseCardListStyle>
      <SimpleGrid
        cols={2}
        spacing="xs"
        verticalSpacing="xs"
        style={{ width: '100%' }}
        breakpoints={[{ maxWidth: '62rem', cols: 1 }]}
      >
        {courses
          .filter(course => {
            return displayBookmarked ? isBookmarked[course.id] : true;
          })
          .map(
            ({
              id,
              title,
              url,
              company,
              superCategories,
              subCategories,
              languages,
              frameworks,
              dates,
              cost,
              period,
              free,
              kdt,
            }: CourseCardProps) => (
              <CourseCard
                key={id}
                id={id}
                title={title}
                url={url}
                company={company}
                superCategories={superCategories}
                subCategories={subCategories}
                languages={languages}
                frameworks={frameworks}
                dates={dates}
                cost={cost}
                period={period}
                free={free}
                kdt={kdt}
              />
            )
          )}
      </SimpleGrid>
    </CourseCardListStyle>
  );
};

interface CourseCardListProps {
  courses: Course[];
  displayBookmarked?: boolean;
  bookmarkedCourseIds: number[];
}

export default CourseCardList;
