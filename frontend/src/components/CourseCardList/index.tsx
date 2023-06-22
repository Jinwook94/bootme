import CourseCard from '../CourseCard';
import { CourseCardListStyle } from './style';
import React from 'react';
import { SimpleGrid } from '@mantine/core';

const CourseCardList = ({ courses }: CourseCardListProps) => {
  return (
    <CourseCardListStyle>
      <SimpleGrid
        cols={2}
        spacing="xs"
        verticalSpacing="xs"
        style={{ width: '100%' }}
        breakpoints={[{ maxWidth: '62rem', cols: 1 }]}
      >
        {courses.map((course: Course) => (
          <CourseCard key={course.id} {...course} />
        ))}
      </SimpleGrid>
    </CourseCardListStyle>
  );
};

interface CourseCardListProps {
  courses: Course[];
}

export default CourseCardList;
