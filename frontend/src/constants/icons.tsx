import React from 'react';

// https://fonts.google.com/icons

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

export const CloseIconBlack = () => (
  <svg className="mw ap" width="29" height="29">
    <path
      d="M20.13 8.11l-5.61 5.61-5.6-5.61-.81.8 5.61 5.61-5.61 5.61.8.8 5.61-5.6 5.61 5.6.8-.8-5.6-5.6 5.6-5.62"
      fillRule="evenodd"
    ></path>
  </svg>
);

export const CloseIconGray = () => (
  <svg className="mw ap" width="29" height="29">
    <path
      d="M20.13 8.11l-5.61 5.61-5.6-5.61-.81.8 5.61 5.61-5.61 5.61.8.8 5.61-5.6 5.61 5.6.8-.8-5.6-5.6 5.6-5.62"
      fillRule="evenodd"
      style={{ fill: 'rgba(117, 117, 117, 1)' }}
    ></path>
  </svg>
);

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

export const KakaoLogo = () => (
  <svg
    version="1.0"
    xmlns="http://www.w3.org/2000/svg"
    width="85%"
    height="85%"
    viewBox="0 0 512.000000 512.000000"
    preserveAspectRatio="xMidYMid meet"
  >
    <g transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)" fill="#000000" stroke="none">
      <path
        d="M2345 4914 c-430 -34 -825 -141 -1175 -319 -865 -441 -1312 -1253
-1128 -2050 120 -522 484 -974 1039 -1290 l106 -60 -123 -460 c-74 -277 -121
-469 -117 -483 6 -27 39 -52 68 -52 12 0 260 161 552 358 l531 358 108 -14
c160 -19 558 -19 723 2 537 66 1015 250 1400 539 115 86 300 263 384 367 418
518 519 1154 275 1728 -304 716 -1074 1231 -2028 1357 -119 16 -505 28 -615
19z"
      />
    </g>
  </svg>
);

export const NotificationIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    height="1.7rem"
    width="1.7rem"
    viewBox="0 96 960 960"
    style={{ fill: 'rgb(156 163 175)' }}
  >
    <path d="M160 856v-60h84V490q0-84 49.5-149.5T424 258v-29q0-23 16.5-38t39.5-15q23 0 39.5 15t16.5 38v29q81 17 131 82.5T717 490v306h83v60H160Zm320-295Zm0 415q-32 0-56-23.5T400 896h160q0 33-23.5 56.5T480 976ZM304 796h353V490q0-74-51-126t-125-52q-74 0-125.5 52T304 490v306Z" />
  </svg>
);

export const NotificationActiveIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    height="1.7rem"
    width="1.7rem"
    viewBox="0 96 960 960"
    style={{ fill: 'rgb(156 163 175)' }}
  >
    <path d="M160 856v-60h84V490q0-84 49.5-149.5T424 258v-29q0-23 16.5-38t39.5-15q23 0 39.5 15t16.5 38v29q81 17 131 82.5T717 490v306h83v60H160Zm320-295Zm0 415q-32 0-56-23.5T400 896h160q0 33-23.5 56.5T480 976ZM304 796h353V490q0-74-51-126t-125-52q-74 0-125.5 52T304 490v306Z" />
    <ellipse style={{ fill: '#c51616', strokeWidth: '20' }} id="redDot" cx="650" cy="380" rx="100" ry="100" />
  </svg>
);

export const DotIcon = () => (
  <svg
    width="4"
    height="4"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
    className="G0E1OVA5O87wEsrH564S"
    style={{ margin: '0 0.5rem' }}
  >
    <circle r="2" transform="matrix(1 0 0 -1 2 2)" fill="#C4C4C4"></circle>
  </svg>
);

export const ProfileIcon = () => (
  <svg
    width="100%"
    height="100%"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
    className="feather feather-user "
  >
    <g>
      <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
      <circle cx="12" cy="7" r="4"></circle>
    </g>
  </svg>
);

export const BookmarkIcon = () => (
  <svg
    width="100%"
    height="100%"
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    strokeWidth="1.5"
    stroke="currentColor"
    aria-hidden="true"
    className="h-5 w-5 text-gray-400 hover:cursor-pointer hover:text-blue-500 dark:hover:text-blue-200"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      d="M17.593 3.322c1.1.128 1.907 1.077 1.907 2.185V21L12 17.25 4.5 21V5.507c0-1.108.806-2.057 1.907-2.185a48.507 48.507 0 0111.186 0z"
    ></path>
  </svg>
);

export const LogoutIcon = () => (
  <svg
    width="100%"
    height="100%"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
    className="feather feather-log-out "
  >
    <g>
      <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
      <polyline points="16 17 21 12 16 7"></polyline>
      <line x1="21" y1="12" x2="9" y2="12"></line>
    </g>
  </svg>
);
