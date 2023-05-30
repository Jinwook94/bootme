import React, { createContext, useContext, useEffect, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useFilters } from './useFilters';
import { useRecoilState, useRecoilValue } from 'recoil';
import { currentPageHome, currentView } from '../recoilState';
import { BOOKMARK, HOME } from '../constants/pages';

const CourseContext = createContext<CourseContextProps>({
  currentCourses: [],
  courseCount: 0,
  maxPage: 0,
  size: 12,
  sortOption: '',
  handleSorting: () => {},
  fetchCourses: () => Promise.resolve(),
});

export const CourseProvider = ({ children }: { children: React.ReactNode }) => {
  const { selectedFilters } = useFilters();
  const [currentCourses, setCurrentCourses] = useState<Course[]>([]);
  const [sortOption, setSortOption] = useState('popular');
  const [maxPage, setMaxPage] = useState<number>(1);
  const [size] = useState<number>(12);
  const [currentPage, setCurrentPage] = useRecoilState(currentPageHome);
  const [courseCount, setCourseCount] = useState<number>();
  const view = useRecoilValue(currentView);

  const fetchCourses = (sort: string, page: number) => {
    const filterParams = Object.entries(selectedFilters).flatMap(([key, value]) => {
      if (value && value.length) {
        return value.map(option => `${key}=${encodeURIComponent(option)}`);
      }
      return [];
    });

    const filterString = filterParams.join('&');

    return fetcher
      .get(`/courses?${filterString}`, {
        params: {
          sort: sort,
          page: page,
          size: size,
        },
      })
      .then(r => {
        setMaxPage(r.data.totalPages);
        setCourseCount(r.data.totalElements);
        setCurrentCourses(r.data.content);
      })
      .catch(e => {
        console.log(e);
        return Promise.reject(e);
      });
  };

  const handleSorting = (option: string) => {
    setSortOption(option);
  };

  useEffect(() => {
    view === HOME && fetchCourses(sortOption, currentPage);
  }, [currentPage, view]);

  useEffect(() => {
    if (view === HOME || view === BOOKMARK) {
      setCurrentPage(1);
      fetchCourses(sortOption, 1);
    }
  }, [selectedFilters, sortOption]);

  return (
    <CourseContext.Provider
      value={{
        currentCourses,
        courseCount,
        maxPage,
        size,
        sortOption,
        handleSorting,
        fetchCourses,
      }}
    >
      {children}
    </CourseContext.Provider>
  );
};

export const useCourses = () => useContext(CourseContext);

interface CourseContextProps {
  currentCourses: Course[];
  courseCount: number | undefined;
  maxPage: number;
  size: number;
  sortOption: string;
  handleSorting: (option: string) => void;
  fetchCourses: (sort: string, page: number) => Promise<void>;
}
