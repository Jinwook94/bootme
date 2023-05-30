import { fetcher } from '../api/fetcher';
import { useState } from 'react';
import { useSnackbar } from './useSnackbar';
import SNACKBAR_MESSAGE, { CHECK, EXCLAMATION } from '../constants/snackbar';

const useCourse = () => {
  const [course, setCourse] = useState<Course>();
  const { showSnackbar } = useSnackbar();

  const fetchCourse = async (id: string | undefined) => {
    try {
      const response = await fetcher.get(`/courses/${id}`);
      setCourse(response.data);
      return response.data;
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_COURSE_FETCH + ': ' + e.response.data.message, EXCLAMATION);
    }
  };

  const sendCourseDetail = async (courseId: number, detail: string): Promise<void> => {
    try {
      await fetcher.patch(`/courses/${courseId}/detail`, JSON.stringify({ detail: detail }), {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      await showSnackbar(SNACKBAR_MESSAGE.SUCCESS_UPLOAD, CHECK);
      setTimeout(() => window.location.reload(), 1500);
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_UPLOAD + ': ' + e.response.data.message, EXCLAMATION);
    }
  };

  return { fetchCourse, sendCourseDetail, course };
};

export default useCourse;
