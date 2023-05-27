import React from 'react';
import { Route, Routes } from 'react-router-dom';
import PATH from './constants/path';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import { NaverLoginRedirect } from './components/LoginModal/NaverLogin';
import { KakaoLoginRedirect } from './components/LoginModal/KakaoLogin';
import CourseDetailPage from './pages/CourseDetailPage';
import PrivateRoute from './routes/PrivateRoute';
import BookmarkPage from './pages/BookmarkPage';
import Test from './pages/Test';
import { useSnackbar } from './hooks/useSnackbar';
import Header from './components/Header';
import Snackbar from './components/Snackbar';

const App = () => {
  const { isVisible, message } = useSnackbar();
  return (
    <>
      <Header />
      <Routes>
        <Route path={PATH.HOME} element={<HomePage />} />
        <Route path={PATH.LOGIN} element={<LoginPage />} />
        <Route path={PATH.OAUTH.NAVER} element={<NaverLoginRedirect />} />
        <Route path={PATH.OAUTH.KAKAO} element={<KakaoLoginRedirect />} />
        <Route path={`${PATH.COURSE}/:id`} element={<CourseDetailPage />} />
        <Route path={'/test'} element={<Test />} />
        <Route element={<PrivateRoute />}>
          <Route path={PATH.BOOKMARKS} element={<BookmarkPage />} />
        </Route>
      </Routes>
      {isVisible && <Snackbar message={message} />}
    </>
  );
};

export default App;
