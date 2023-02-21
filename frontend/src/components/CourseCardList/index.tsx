import CourseCard, { CourseCardProps } from '../CourseCard';
import { CourseCardListStyle } from './style';
import React from 'react';
import useBookmarks from '../../hooks/useBookmarks';

const CourseCardList = ({ currentCards }: CourseCardListProps) => {
  const { bookmarkedCourses, isLoading } = useBookmarks();

  return (
    <CourseCardListStyle>
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        currentCards.map(
          ({ id, title, url, company, categories, stacks, dates, period, cost, costType }: CourseCardProps) => (
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
              bookmarked={bookmarkedCourses.includes(id)}
            />
          )
        )
      )}
    </CourseCardListStyle>
  );
};

interface CourseCardListProps {
  currentCards: Course[];
}

export default CourseCardList;
