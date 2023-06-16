import React from 'react';
import styled from 'styled-components';
import PATH from '../../constants/path';
import { Link } from 'react-router-dom';

const NotFoundPage = () => {
  return (
    <NotFoundWrapper>
      <h1 style={{ marginBottom: '1rem' }}>404 - Not Found</h1>
      <p>페이지를 찾을 수 없습니다.</p>
      <p>
        <Link to={PATH.HOME}>홈으로 돌아가기</Link>
      </p>
    </NotFoundWrapper>
  );
};

export default NotFoundPage;

const NotFoundWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 30vh;
  text-align: center;

  a {
    color: blue;
    text-decoration: underline;
  }
`;
