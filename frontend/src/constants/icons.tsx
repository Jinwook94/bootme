import React from 'react';

// https://fonts.google.com/icons

// eslint-disable-next-line react/prop-types
export const CloseIcon = () => {
  return (
    <>
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20,300,0,0"
      />
      <span className="material-symbols-outlined">close</span>
    </>
  );
};

export const CheckIcon = () => (
  <svg
    viewBox="0 0 32 32"
    xmlns="http://www.w3.org/2000/svg"
    aria-hidden="true"
    role="presentation"
    focusable="false"
    style={{
      display: 'block',
      fill: 'none',
      height: '16px',
      width: '16px',
      stroke: 'white',
      strokeWidth: '4',
      overflow: 'visible',
    }}
  >
    <path fill="none" d="m4 16.5 8 8 16-16"></path>
  </svg>
);
export const ResetIcon = () => (
  <svg
    style={{
      verticalAlign: 'middle',
      marginRight: '0.25rem',
      color: 'rgb(38, 55, 71)',
    }}
    width="1rem"
    height="1rem"
    viewBox="0 0 24 24"
    fill="currentColor"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      fillRule="evenodd"
      clipRule="evenodd"
      d="M17.65 6.35C16.2 4.9 14.21 4 12 4C7.58001 4 4.01001 7.58 4.01001 12C4.01001 16.42 7.58001 20 12 20C15.73 20 18.84 17.45 19.73 14H17.65C16.83 16.33 14.61 18 12 18C8.69001 18 6.00001 15.31 6.00001 12C6.00001 8.69 8.69001 6 12 6C13.66 6 15.14 6.69 16.22 7.78L13 11H20V4L17.65 6.35Z"
      fill="currentColor"
    ></path>
  </svg>
);

export const CaretIcon = ({ rotation }: { rotation: number }) => (
  <svg
    style={{ verticalAlign: 'middle', transform: `rotate(${rotation}deg)`, transition: 'all 0.3s linear 0s' }}
    xmlns="http://www.w3.org/2000/svg"
    fill="currentColor"
    viewBox="0 0 24 24"
    width="1rem"
    height="1rem"
  >
    <path d="m2 8 1.775-1.77L12 14.45l8.225-8.22L22 8 12 18 2 8Z"></path>
  </svg>
);

export const CaretUpIcon = () => (
  <svg
    style={{ marginLeft: '0.25rem', overflow: 'hidden', verticalAlign: 'middle', transform: 'rotate(180deg)' }}
    xmlns={'http://www.w3.org/2000/svg'}
    fill={'#0078FF'}
    viewBox={'0 0 24 24'}
    width={'0.875rem'}
    height={'0.875rem'}
  >
    <path d="m2 8 1.775-1.77L12 14.45l8.225-8.22L22 8 12 18 2 8Z"></path>
  </svg>
);

export const CaretDownIcon = () => (
  <svg
    style={{ marginLeft: '0.25rem', overflow: 'hidden', verticalAlign: 'middle', transform: 'rotate(0deg)' }}
    xmlns={'http://www.w3.org/2000/svg'}
    fill={'#0078FF'}
    viewBox={'0 0 24 24'}
    width={'0.875rem'}
    height={'0.875rem'}
  >
    <path d="m2 8 1.775-1.77L12 14.45l8.225-8.22L22 8 12 18 2 8Z"></path>
  </svg>
);
