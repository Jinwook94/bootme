import { useState, useMemo } from 'react';

const useSorting = (courseCards: Course[]): useSortingProps => {
  const [sortOption, setSortOption] = useState('popular');

  // todo: Course API 에 북마크 저장 횟수, 등록일자 가져오도록 변경한 후 정렬 수정 필요
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
          if (b.cost === a.cost) {
            if (b.id === a.id) {
              return b.period - a.period;
            }
            return b.id - a.id;
          }
          return b.cost - a.cost;
        });
      case 'newest':
        return [...courseCards].sort((a, b) => {
          if (a.id === b.id) {
            if (b.cost === a.cost) {
              return b.period - a.period;
            }
            return b.cost - a.cost;
          }
          return a.id - b.id;
        });
      case 'bookmark':
        return [...courseCards].sort((a, b) => {
          if (b.period === a.period) {
            if (b.cost === a.cost) {
              return b.id - a.id;
            }
            return b.cost - a.cost;
          }
          return b.period - a.period;
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
