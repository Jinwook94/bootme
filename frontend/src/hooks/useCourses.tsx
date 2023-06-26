import React, { createContext, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useCourseFilters } from './useFilters';
import { COURSE_FILTERS } from '../constants/filters';
import { SORT_OPTION } from '../constants/others';

const CourseContext = createContext<CourseContextProps>({
  currentCourses: [],
  courseCount: 0,
  maxPage: 0,
  size: 12,
  sortOption: '',
  isLoading: false,
  handleSorting: () => {},
  fetchCourses: () => Promise.resolve(),
  onSearch: () => {},
});

export const CourseProvider = ({ children }: { children: React.ReactNode }) => {
  const { selectedFilters, clearAndAddFilter } = useCourseFilters();
  const [currentCourses, setCurrentCourses] = useState<Course[]>([]);
  const [sortOption, setSortOption] = useState<SortOption>(SORT_OPTION.CLICKS);
  const [maxPage, setMaxPage] = useState<number>(1);
  const [size] = useState<number>(12);
  const [courseCount, setCourseCount] = useState<number>();
  const [isLoading, setIsLoading] = useState(false);

  const fetchCourses = (sort: string, page: number) => {
    setIsLoading(true);
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
        setIsLoading(false);
      })
      .catch(e => {
        console.log(e);
        setIsLoading(false);
        return Promise.reject(e);
      });
  };

  const handleSorting = (value: string) => {
    setSortOption(value as SortOption);
  };

  const onSearch = (value: string) => {
    clearAndAddFilter(COURSE_FILTERS.SEARCH.filterName, value);
  };

  return (
    <CourseContext.Provider
      value={{
        currentCourses,
        courseCount,
        maxPage,
        size,
        sortOption,
        isLoading,
        handleSorting,
        fetchCourses,
        onSearch,
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
  isLoading: boolean;
  handleSorting: (value: string) => void;
  fetchCourses: (sort: string, page: number) => Promise<void>;
  onSearch: (value: string) => void;
}
