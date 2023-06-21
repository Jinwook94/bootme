import React, { createContext, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useLogin } from './useLogin';
import { useSnackbar } from './useSnackbar';
import SNACKBAR_MESSAGE, { CHECK, EXCLAMATION } from '../constants/snackbar';
import { BOOKMARK_TYPE } from '../constants/others';

const BookmarkContext = createContext<BookmarkContextProps>({
  isBookmarked: {},
  setIsBookmarked: () => {},
  courseBookmarksIds: [],
  setCourseBookmarksIds: () => {},
  currentCourses: [],
  setCurrentCourses: () => {},
  fetchCourseBookmarks: async () => [],
  fetchCourseBookmarksIds: async () => [],
  handleBookmark: () => {},
  maxPage: 1,
  size: 12,
  courseCount: 0,
});

export const BookmarkProvider = ({ children }: BookmarkProviderProps) => {
  const { isLogin } = useLogin();
  const { showSnackbar } = useSnackbar();
  const memberId = localStorage.getItem('memberId');
  const [isBookmarked, setIsBookmarked] = useState<{ [key: string]: boolean }>({});
  const [courseBookmarksIds, setCourseBookmarksIds] = useState<number[]>([]);
  const [maxPage, setMaxPage] = useState<number>(1);
  const [size] = useState<number>(12);
  const [courseCount, setCourseCount] = useState<number>();
  const [currentCourses, setCurrentCourses] = useState<Course[]>([]);

  const fetchCourseBookmarks = async (page: number) => {
    const endpoint = `/bookmarks/${memberId}/courses`;
    if (isLogin) {
      return fetcher
        .get(endpoint, {
          params: {
            page: page,
            size: 12,
          },
        })
        .then(r => {
          setMaxPage(r.data.totalPages);
          setCurrentCourses(r.data.content);
          setCourseCount(r.data.totalElements);
          return r.data.content;
        })
        .catch(error => {
          console.log(error);
          return [];
        });
    }
  };

  const fetchCourseBookmarksIds = () => {
    const endpoint = `/bookmarks/${memberId}/courses/ids`;
    if (isLogin) {
      return fetcher
        .get(endpoint, {})
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
          return [];
        });
    }
  };

  const handleBookmark = async (id: number, type: BookmarkType) => {
    const typePaths: Record<BookmarkType, string> = {
      [BOOKMARK_TYPE.COURSE]: 'courses',
      [BOOKMARK_TYPE.POST]: 'posts',
      [BOOKMARK_TYPE.COMMENT]: 'comments',
    };

    const path = typePaths[type];
    const endpoint = `/bookmarks/${memberId}/${path}/${id}`;
    const method = isBookmarked[id] ? 'DELETE' : 'POST';
    if (isLogin) {
      try {
        await fetcher(endpoint, {
          method: method,
        });
        setIsBookmarked(prevState => {
          return { ...prevState, [id]: !prevState[id] };
        });

        if (method === 'DELETE') {
          setCourseBookmarksIds(prevState => prevState.filter(courseId => courseId !== id));
          showSnackbar(SNACKBAR_MESSAGE.SUCCESS_DELETE_BOOKMARK, CHECK);
        } else {
          setCourseBookmarksIds(prevState => [...prevState, id]);
          showSnackbar(SNACKBAR_MESSAGE.SUCCESS_ADD_BOOKMARK, CHECK);
        }
      } catch (error) {
        console.error(`Failed to ${method} bookmark for course ${id}:`, error);
      }
    } else if (!isLogin) {
      showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, EXCLAMATION);
    }
  };

  return (
    <BookmarkContext.Provider
      value={{
        isBookmarked,
        setIsBookmarked,
        courseBookmarksIds,
        setCourseBookmarksIds,
        currentCourses,
        setCurrentCourses,
        fetchCourseBookmarks,
        fetchCourseBookmarksIds,
        handleBookmark,
        maxPage,
        size,
        courseCount,
      }}
    >
      {children}
    </BookmarkContext.Provider>
  );
};

export const useBookmarks = () => useContext(BookmarkContext);

interface BookmarkContextProps {
  isBookmarked: { [key: string]: boolean };
  setIsBookmarked: React.Dispatch<React.SetStateAction<{ [key: string]: boolean }>>;
  courseBookmarksIds: number[];
  setCourseBookmarksIds: React.Dispatch<number[]>;
  currentCourses: Course[];
  setCurrentCourses: React.Dispatch<Course[]>;
  fetchCourseBookmarks: (page: number) => Promise<Course[]> | undefined;
  fetchCourseBookmarksIds: () => Promise<number[]> | undefined;
  handleBookmark: (id: number, type: BookmarkType) => void;
  maxPage: number;
  size: number;
  courseCount: number | undefined;
}

export type BookmarkTypeKey = 'COURSE' | 'POST' | 'COMMENT';
export type BookmarkType = typeof BOOKMARK_TYPE[BookmarkTypeKey];

interface BookmarkProviderProps {
  children: React.ReactNode;
}
