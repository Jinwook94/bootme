import { Spin } from 'antd';
import React from 'react';
import styled from 'styled-components';
import { Overlay } from '@mantine/core';

const Wrapper = styled.div`
  position: fixed;
  top: 25%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 9999;
`;

const LoadingSpinner = ({ overlay = false }: LoadingSpinnerProps) => {
  return (
    <>
      {overlay && (
        <>
          <Overlay color="#F1F3F5" opacity={0.85} />
          <Wrapper>
            <Spin size="large" />
          </Wrapper>
        </>
      )}
      {!overlay && (
        <Wrapper>
          <Spin size="large" />
        </Wrapper>
      )}
    </>
  );
};

export default LoadingSpinner;

interface LoadingSpinnerProps {
  overlay?: boolean;
}
