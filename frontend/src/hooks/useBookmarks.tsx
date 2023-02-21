import { useCallback, useEffect, useState } from 'react';
import { fetcher } from '../api/fetcher';

const useBookmarks = () => {
  const [isBookmarked, setIsBookmarked] = useState<boolean>(false);
  const memberId = localStorage.getItem('MemberId');
  const [bookmarkedCourses, setBookmarkedCourses] = useState<number[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const getBookmarkedCourses = () => {
    const endpoint = `/member/${memberId}/bookmarks`;
    fetcher
      .get(endpoint, {})
      .then(response => {
        setBookmarkedCourses(response.data);
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  useEffect(() => {
    getBookmarkedCourses();
  }, []);

  const handleBookmark = useCallback(
    async (id: number) => {
      const endpoint = `/member/${memberId}/bookmarks/${id}`;
      const method = isBookmarked ? 'DELETE' : 'POST';

      try {
        await fetcher(endpoint, {
          method: method,
        });
        setIsBookmarked(!isBookmarked);
        getBookmarkedCourses(); // call the function to update bookmarked courses
      } catch (error) {
        console.error(`Failed to ${method} bookmark for course ${id}:`, error);
      }
    },
    [isBookmarked, memberId]
  );

  return {
    isBookmarked,
    setBookmarkedCourses,
    isLoading,
    getBookmarkedCourses,
    setIsBookmarked,
    bookmarkedCourses,
    handleBookmark,
  };
};

export default useBookmarks;
