import React from 'react';
import Home from './pages/Home';
import { Route, Routes } from 'react-router-dom';
import PATH from './constants/path';
import { KakaoLogin } from './components/LoginModal/KakaoLogin';

const App = () => {
  return (
    <>
      <Routes>
        <Route path={PATH.HOME} element={<Home />} />
        <Route path={PATH.OAUTH.KAKAO} element={<KakaoLogin />} />
      </Routes>
    </>
  );
};

export default App;
