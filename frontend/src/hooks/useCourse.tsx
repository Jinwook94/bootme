import { fetcher } from '../api/fetcher';
import { useState } from 'react';

const useCourse = () => {
  const [course, setCourse] = useState<Course>();

  const fetchCourse = async (id: string | undefined) => {
    try {
      const response = await fetcher.get(`/courses/${id}`);
      setCourse(response.data);
    } catch (e) {
      console.log(e);
    }
  };

  return { fetchCourse, course };
};

export default useCourse;
