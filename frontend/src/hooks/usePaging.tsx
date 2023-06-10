import { currentPageBookmark, currentPageHome } from '../recoilState';
import { useRecoilState } from 'recoil';
import PATH from '../constants/path';

const usePaging = (view: string) => {
  const [currentPageInHomePage, setCurrentPageInHomePage] = useRecoilState(currentPageHome);
  const [currentPageInBookmarkPage, setCurrentPageInBookmarkPage] = useRecoilState(currentPageBookmark);

  const setCurrentPage = view === PATH.HOME ? setCurrentPageInHomePage : setCurrentPageInBookmarkPage;

  const handlePageChange = (page: number | ((currVal: number) => number)) => {
    setCurrentPage(page);
    window.requestAnimationFrame(() => {
      window.scrollTo({
        top: 0,
        behavior: 'smooth',
      });
    });
  };

  return { handlePageChange };
};

export default usePaging;
