import React, { createContext, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useLogin } from './useLogin';

const BookmarkContext = createContext<BookmarkContextProps>({
  isBookmarked: {},
  setIsBookmarked: () => {},
  bookmarkedCourses: [],
  setBookmarkedCourses: () => {},
  isLoading: true,
  setIsLoading: () => {},
  getBookmarkedCourses: async () => [],
  handleBookmark: () => {},
});

export const BookmarkProvider = ({ children }: BookmarkProviderProps) => {
  const { isLogin, handleLoginModal } = useLogin();
  const memberId = localStorage.getItem('MemberId');
  const [isBookmarked, setIsBookmarked] = useState<{ [key: string]: boolean }>({});
  const [bookmarkedCourses, setBookmarkedCourses] = useState<number[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const getBookmarkedCourses = () => {
    const endpoint = `/member/${memberId}/bookmarks`;
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
      } catch (error) {
        console.error(`Failed to ${method} bookmark for course ${id}:`, error);
      }
    } else if (!isLogin) {
      handleLoginModal();
    }
  };

  return (
    <BookmarkContext.Provider
      value={{
        isBookmarked,
        setIsBookmarked,
        setBookmarkedCourses,
        isLoading,
        setIsLoading,
        getBookmarkedCourses,
        bookmarkedCourses,
        handleBookmark,
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
  bookmarkedCourses: number[];
  setBookmarkedCourses: React.Dispatch<number[]>;
  isLoading: boolean;
  setIsLoading: React.Dispatch<boolean>;
  getBookmarkedCourses: () => Promise<number[]>;
  handleBookmark: (id: number) => void;
}

interface BookmarkProviderProps {
  children: React.ReactNode;
}
