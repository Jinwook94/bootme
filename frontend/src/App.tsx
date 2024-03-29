import React from 'react';
import { Route, Routes } from 'react-router-dom';
import PATH from './constants/path';
import CourseListPage from './pages/CourseListPage';
import LoginPage from './pages/LoginPage';
import { NaverLoginRedirect } from './components/LoginModal/NaverLogin';
import { KakaoLoginRedirect } from './components/LoginModal/KakaoLogin';
import CourseDetailPage from './pages/CourseDetailPage';
import PrivateRoute from './routes/PrivateRoute';
import Header from './components/Header';
import Snackbar from './components/@common/Snackbar';
import PartnerPage from './pages/PartnerPage';
import PostListPage from './pages/PostListPage';
import PostDetailPage from './pages/PostDetailPage';
import PostWritePage from './pages/PostWritePage';
import PostSearchMobilePage from './pages/PostSearchMobilePage';
import Test from './pages/Test';
import { useSnackbar } from './hooks/useSnackbar';
import NotFoundPage from './pages/NotFoundPage';
import ProfilePage from './pages/ProfilePage';
import BookmarkCoursePage from './pages/BookmarkCoursePage';
import BookmarkPostPage from './pages/BookmarkPostPage';
import PromptGeneratorPage from './pages/PromptGeneratorPage';
import RouteChangeTracker from './utils/RouteChangeTracker';

const App = () => {
  const { isVisible, message, displayIcon } = useSnackbar();
  return (
    <>
      <Header />
      <RouteChangeTracker />
      <Routes>
        <Route path={PATH.HOME} element={<PostListPage />} />
        <Route path={PATH.LOGIN} element={<LoginPage />} />
        <Route path={PATH.OAUTH.NAVER} element={<NaverLoginRedirect />} />
        <Route path={PATH.OAUTH.KAKAO} element={<KakaoLoginRedirect />} />
        <Route path={PATH.COURSE.LIST} element={<CourseListPage />} />
        <Route path={`${PATH.COURSE.DETAIL}/:id`} element={<CourseDetailPage />} />
        <Route path={PATH.PROMPT.GENERATOR} element={<PromptGeneratorPage />} />
        <Route path={PATH.POST.LIST} element={<PostListPage />} />
        <Route path={`${PATH.POST.DETAIL}/:id`} element={<PostDetailPage />} />
        <Route path={PATH.POST.SEARCH} element={<PostSearchMobilePage />} />
        <Route path={'/test'} element={<Test />} />
        <Route element={<PrivateRoute />}>
          <Route path={PATH.PROFILE} element={<ProfilePage />} />
          <Route path={PATH.BOOKMARK.COURSE} element={<BookmarkCoursePage />} />
          <Route path={PATH.BOOKMARK.POST} element={<BookmarkPostPage />} />
          <Route path={PATH.POST.WRITE} element={<PostWritePage />} />
        </Route>
        <Route path={PATH.PARTNER} element={<PartnerPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
      {isVisible && <Snackbar message={message} displayIcon={displayIcon} />}
    </>
  );
};

export default App;
