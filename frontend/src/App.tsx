import React from 'react';
import Home from './pages/Home';
import { Route, Routes } from 'react-router-dom';
import PATH from './constants/path';
import { KakaoLoginRedirect } from './components/LoginModal/KakaoLogin';
import Bookmarks from './pages/Bookmarks';
import PrivateRoute from './routes/PrivateRoute';
import Login from './pages/Login';
import Test from './pages/Test';

const App = () => {
  return (
    <>
      <Routes>
        <Route path={PATH.HOME} element={<Home />} />
        <Route path={PATH.OAUTH.KAKAO} element={<KakaoLoginRedirect />} />
        <Route path={PATH.LOGIN} element={<Login />} />
        <Route path={'/test'} element={<Test />} />
        <Route element={<PrivateRoute />}>
          <Route path={PATH.BOOKMARKS} element={<Bookmarks />} />
        </Route>
      </Routes>
    </>
  );
};

export default App;
