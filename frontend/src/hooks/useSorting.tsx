import { useState, useMemo } from 'react';

const useSorting = (courseCards: Course[]): useSortingProps => {
  const [sortOption, setSortOption] = useState('popular');

  // todo: Course API 에 북마크 저장 횟수, 등록일자 가져오도록 변경한 후 정렬 수정 필요
  const sortedCards = useMemo(() => {
    switch (sortOption) {
      case 'popular':
        return [...courseCards].sort((a, b) => b.cost - a.cost);
      case 'newest':
        return [...courseCards].sort((a, b) => a.id - b.id);
      // return courseCards.sort((a, b) => new Date(b.dates.courseStartDate) - new Date(a.dates.courseStartDate));
      case 'bookmark':
        return [...courseCards].sort((a, b) => b.period - a.period);
      // return courseCards.sort((a, b) => b.bookmarked - a.bookmarked);
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
