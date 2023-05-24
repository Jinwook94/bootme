import React from 'react';
import { currentPageBookmark, currentPageHome, currentView } from '../recoilState';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { HOME } from '../constants/pages';

const usePaging = (page: string, maxPage: number) => {
  const [homePage, setHomePage] = useRecoilState(currentPageHome);
  const [bookmarkPage, setBookmarkPage] = useRecoilState(currentPageBookmark);
  const setCurrentView = useSetRecoilState(currentView);

  const currentPage = page === HOME ? homePage : bookmarkPage;
  const setCurrentPage = page === HOME ? setHomePage : setBookmarkPage;

  const handleNumberClick =
    (number: number): React.MouseEventHandler =>
    () => {
      setCurrentPage(number);
      setCurrentView(page);
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
    setCurrentView(page);
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
    setCurrentView(page);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  return { handleNumberClick, handleNextClick, handlePrevClick };
};

export default usePaging;
