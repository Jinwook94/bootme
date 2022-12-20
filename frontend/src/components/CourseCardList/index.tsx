import CourseCard, { CourseCardProps } from '../CourseCard';
import { CourseCardListStyle } from './style';
import React from 'react';

const CourseCardList = ({ cards }: CourseCardListProps) => {
  return (
    <CourseCardListStyle>
      {cards.map(({ id, title, url, company, tags, location }: CourseCardProps) => (
        <CourseCard key={id} title={title} url={url} company={company} tags={tags} location={location} id={id} />
      ))}
    </CourseCardListStyle>
  );
};

interface CourseCardListProps {
  cards: Course[];
}

export default CourseCardList;
