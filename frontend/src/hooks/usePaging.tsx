import { useEffect } from 'react';
import { currentPageBookmark, currentPageHome, currentView } from '../recoilState';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { HOME } from '../constants/pages';

const usePaging = (view: string) => {
  const [currentPageInHomePage, setCurrentPageInHomePage] = useRecoilState(currentPageHome);
  const [currentPageInBookmarkPage, setCurrentPageInBookmarkPage] = useRecoilState(currentPageBookmark);
  const setCurrentView = useSetRecoilState(currentView);

  const currentPage = view === HOME ? currentPageInHomePage : currentPageInBookmarkPage;
  const setCurrentPage = view === HOME ? setCurrentPageInHomePage : setCurrentPageInBookmarkPage;

  const handlePageChange = (page: number | ((currVal: number) => number)) => {
    setCurrentPage(page);
    window.requestAnimationFrame(() => {
      window.scrollTo({
        top: 0,
        behavior: 'smooth',
      });
    });
  };

  useEffect(() => {
    setCurrentView(view);
  }, [currentPage]);

  return { handlePageChange };
};

export default usePaging;
