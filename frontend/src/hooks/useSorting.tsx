import { useState, useMemo } from 'react';

const useSorting = (courseCards: Course[]): useSortingProps => {
  const [sortOption, setSortOption] = useState('popular');

  /**
   * 정렬 옵션별 우선순위
   *  인기순: 인기순 > 등록순 > 북마크순   ex) 인기순 정렬 선택시 클릭수가 같다면 최근 등록순으로 정렬
   *  등록순: 등록순 > 인기순 > 북마크순
   *  북마크순: 북마크순 > 인기순 > 등록순
   */
  const sortedCards = useMemo(() => {
    switch (sortOption) {
      case 'popular':
        return [...courseCards].sort((a, b) => {
          if (b.clicks === a.clicks) {
            if (b.createdAt === a.createdAt) {
              return b.bookmarks - a.bookmarks;
            }
            return a.createdAt - b.createdAt;
          }
          return b.clicks - a.clicks;
        });
      case 'newest':
        return [...courseCards].sort((a, b) => {
          if (a.createdAt === b.createdAt) {
            if (b.clicks === a.clicks) {
              return b.bookmarks - a.bookmarks;
            }
            return b.clicks - a.clicks;
          }
          return a.createdAt - b.createdAt;
        });
      case 'bookmark':
        return [...courseCards].sort((a, b) => {
          if (b.bookmarks === a.bookmarks) {
            if (b.clicks === a.clicks) {
              return a.createdAt - b.createdAt;
            }
            return b.clicks - a.clicks;
          }
          return b.bookmarks - a.bookmarks;
        });
      default:
        return courseCards;
    }
  }, [courseCards, sortOption]);

  const handleSorting = (option: string) => {
    setSortOption(option);
  };

  return {
    sortedCards,
    sortOption,
    handleSorting,
  };
};

export default useSorting;

interface useSortingProps {
  sortedCards: Course[];
  sortOption: string;
  handleSorting: (option: string) => void;
}
