import CourseCard, { CourseCardProps } from '../CourseCard';
import { CourseCardListStyle } from './style';
import React from 'react';

const CourseCardList = ({ currentCards }: CourseCardListProps) => {
  return (
    <CourseCardListStyle>
      {currentCards.map(
        ({ id, title, url, company, categories, stacks, dates, period, cost, costType }: CourseCardProps) => (
          <CourseCard
            key={id}
            title={title}
            url={url}
            company={company}
            categories={categories}
            stacks={stacks}
            dates={dates}
            cost={cost}
            costType={costType}
            period={period}
            id={id}
          />
        )
      )}
    </CourseCardListStyle>
  );
};

interface CourseCardListProps {
  currentCards: Course[];
}

export default CourseCardList;
