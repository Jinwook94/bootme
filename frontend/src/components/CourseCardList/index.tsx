import CourseCard, { CourseCardProps } from '../CourseCard';
import { CourseCardListStyle } from './style';
import React from 'react';

const CourseCardList = ({ cards }: CourseCardListProps) => {
  return (
    <CourseCardListStyle>
      {cards.map(({ id, title, url, company, categories, stacks, dates, period, cost }: CourseCardProps) => (
        <CourseCard
          key={id}
          title={title}
          url={url}
          company={company}
          categories={categories}
          stacks={stacks}
          dates={dates}
          cost={cost}
          period={period}
          id={id}
        />
      ))}
    </CourseCardListStyle>
  );
};

interface CourseCardListProps {
  cards: Course[];
}

export default CourseCardList;
