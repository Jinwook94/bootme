import React from 'react';
import Home from './pages/Home';
import { Route, Routes } from 'react-router-dom';
import PATH from './constants/path';
import { KakaoLoginRedirect } from './components/LoginModal/KakaoLogin';

const App = () => {
  return (
    <>
      <Routes>
        <Route path={PATH.HOME} element={<Home />} />
        <Route path={PATH.OAUTH.KAKAO} element={<KakaoLoginRedirect />} />
      </Routes>
    </>
  );
};

export default App;
