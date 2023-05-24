import React, { createContext, useContext, useEffect, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useLogin } from './useLogin';
import { useRecoilState, useRecoilValue } from 'recoil';
import { currentPageBookmark, currentView } from '../recoilState';
import { BOOKMARK } from '../constants/pages';

const BookmarkContext = createContext<BookmarkContextProps>({
  isBookmarked: {},
  setIsBookmarked: () => {},
  bookmarkedCourseIds: [],
  setBookmarkedCourseIds: () => {},
  currentCourses: [],
  setCurrentCourses: () => {},
  isLoading: true,
  setIsLoading: () => {},
  fetchBookmarkCourses: async () => [],
  fetchBookmarkCourseIds: async () => [],
  handleBookmark: () => {},
  maxPage: 1,
});

export const BookmarkProvider = ({ children }: BookmarkProviderProps) => {
  const { isLogin, handleLoginModal } = useLogin();
  const memberId = localStorage.getItem('MemberId');
  const [isBookmarked, setIsBookmarked] = useState<{ [key: string]: boolean }>({});
  const [bookmarkedCourseIds, setBookmarkedCourseIds] = useState<number[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [currentPage] = useRecoilState(currentPageBookmark);
  const [maxPage, setMaxPage] = useState<number>(1);
  const [currentCourses, setCurrentCourses] = useState<Course[]>([]);
  const view = useRecoilValue(currentView);

  const fetchBookmarkCourses = async (page: number) => {
    const endpoint = `/member/${memberId}/bookmarks`;
    return fetcher
      .get(endpoint, {
        params: {
          page: page,
          size: 8,
        },
      })
      .then(r => {
        setMaxPage(r.data.totalPages);
        setCurrentCourses(r.data.content);
        return r.data.content;
      })
      .catch(error => {
        console.log(error);
        return [];
      });
  };

  const fetchBookmarkCourseIds = () => {
    const endpoint = `/member/${memberId}/bookmarks/courseIds`;
    return fetcher
      .get(endpoint, {})
      .then(response => {
        return response.data;
      })
      .catch(error => {
        console.log(error);
        return [];
      });
  };

  const handleBookmark = async (id: number) => {
    const endpoint = `/member/${memberId}/bookmarks/${id}`;
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
          setBookmarkedCourseIds(prevState => prevState.filter(courseId => courseId !== id));
        } else {
          setBookmarkedCourseIds(prevState => [...prevState, id]);
        }
      } catch (error) {
        console.error(`Failed to ${method} bookmark for course ${id}:`, error);
      }
    } else if (!isLogin) {
      handleLoginModal();
    }
  };

  useEffect(() => {
    if (view === BOOKMARK) {
      setIsLoading(true);
      fetchBookmarkCourses(currentPage).then(() => setIsLoading(false));
    }
  }, [currentPage, view]);

  useEffect(() => {
    const memberId = localStorage.getItem('MemberId');
    if (memberId) {
      fetchBookmarkCourseIds().then(response => {
        setBookmarkedCourseIds(response);
        const updatedIsBookmarked: { [key: string]: boolean } = {};
        response.forEach((courseId: number) => {
          updatedIsBookmarked[courseId] = true;
        });
        setIsBookmarked(updatedIsBookmarked);
      });
    }
  }, [currentPage]);

  return (
    <BookmarkContext.Provider
      value={{
        isBookmarked,
        setIsBookmarked,
        bookmarkedCourseIds,
        setBookmarkedCourseIds,
        currentCourses,
        setCurrentCourses,
        isLoading,
        setIsLoading,
        fetchBookmarkCourses,
        fetchBookmarkCourseIds,
        handleBookmark,
        maxPage,
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
  bookmarkedCourseIds: number[];
  setBookmarkedCourseIds: React.Dispatch<number[]>;
  currentCourses: Course[];
  setCurrentCourses: React.Dispatch<Course[]>;
  isLoading: boolean;
  setIsLoading: React.Dispatch<boolean>;
  fetchBookmarkCourses: (page: number) => Promise<Course[]>;
  fetchBookmarkCourseIds: () => Promise<number[]>;
  handleBookmark: (id: number) => void;
  maxPage: number;
}

interface BookmarkProviderProps {
  children: React.ReactNode;
}
