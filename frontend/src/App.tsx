import React from 'react';
import Home from './pages/Home';
import { Route, Routes } from 'react-router-dom';
import PATH from './constants/path';

const App = () => {
  return (
    <>
      <Routes>
        <Route path={PATH.HOME} element={<Home />} />
      </Routes>
    </>
  );
};

export default App;
