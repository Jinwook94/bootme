import { Spin } from 'antd';
import React from 'react';
import styled, { css } from 'styled-components';
import { Overlay } from '@mantine/core';

const Wrapper = styled.div<LoadingSpinnerProps>`
  ${props =>
    props.fixed
      ? css`
          position: fixed;
          top: 25%;
          left: 50%;
          transform: translate(-50%, -50%);
          z-index: 9999;
        `
      : css`
          position: relative;
        `}
`;

const LoadingSpinner = ({ size = 'large', overlay = false, fixed = true }: LoadingSpinnerProps) => {
  return (
    <>
      {overlay && (
        <>
          <Overlay color="#F1F3F5" opacity={0.85} />
          <Wrapper fixed={fixed}>
            <Spin size={size} />
          </Wrapper>
        </>
      )}
      {!overlay && (
        <Wrapper fixed={fixed}>
          <Spin size={size} />
        </Wrapper>
      )}
    </>
  );
};

export default LoadingSpinner;

interface LoadingSpinnerProps {
  size?: 'small' | 'default' | 'large';
  overlay?: boolean;
  fixed?: boolean;
}
