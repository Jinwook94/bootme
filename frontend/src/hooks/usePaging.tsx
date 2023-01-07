import React, { useState } from 'react';

const usePaging = (maxPage: number) => {
  const [currentPage, setCurrentPage] = useState(1);

  const handleNumberClick =
    (number: number): React.MouseEventHandler =>
    () => {
      setCurrentPage(number);
      window.scrollTo({
        top: 0,
        behavior: 'smooth',
      });
    };

  const handlePrevClick = () => {
    if (currentPage <= 1) {
      return;
    }
    setCurrentPage(prev => prev - 1);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  const handleNextClick = () => {
    if (currentPage == maxPage) {
      return;
    }
    setCurrentPage(prev => prev + 1);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  const getCurrentItems = (itemPerPage: number, items: Course[]) => {
    const indexOfLastCard = currentPage * itemPerPage;
    const indexOfFirstCard = indexOfLastCard - itemPerPage;
    return items!.slice(indexOfFirstCard, indexOfLastCard);
  };

  return { currentPage, handleNumberClick, handleNextClick, handlePrevClick, getCurrentItems };
};

export default usePaging;
