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
  <svg viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
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
    viewBox={'0 0 24 24'}
  >
    <path d="m2 8 1.775-1.77L12 14.45l8.225-8.22L22 8 12 18 2 8Z"></path>
  </svg>
);

export const CaretDownIcon = () => (
  <svg
    style={{ marginLeft: '0.25rem', overflow: 'hidden', verticalAlign: 'middle', transform: 'rotate(0deg)' }}
    xmlns={'http://www.w3.org/2000/svg'}
    viewBox={'0 0 24 24'}
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
    stroke="#878a8c"
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

export const BookmarkIcon2 = () => (
  <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" className="ic-24">
    <path
      d="M17 3c1.1 0 2 .9 2 2v16l-7-3-7 3 .01-16c0-1.1.89-2 1.99-2h10zm-4 7V7h-2v3H8v2h3v3h2v-3h3v-2h-3z"
      fillRule="nonzero"
    ></path>
  </svg>
);

export const BookmarkIcon3 = () => (
  <svg version="1.1" viewBox="0 0 96 96" xmlSpace="preserve" xmlns="http://www.w3.org/2000/svg">
    <defs>
      <clipPath id="d">
        <path d="m0 2.2888e-5h72v72h-72z" clipRule="evenodd" />
      </clipPath>
      <clipPath id="c">
        <path d="m9.54e-7 3.0518e-5h72v72h-72z" clipRule="evenodd" />
      </clipPath>
      <clipPath id="b">
        <path d="m-3.815e-6 3.0518e-5h72v72h-72z" clipRule="evenodd" />
      </clipPath>
      <clipPath id="a">
        <path d="m9.54e-7 3.0518e-5h72v72h-72z" clipRule="evenodd" />
      </clipPath>
    </defs>
    <g transform="matrix(1.3333 0 0 -1.3333 0 96)">
      <g clipPath="url(#d)">
        <path d="m0 7.629e-6h72v72h-72z" fill="#fff" fillRule="evenodd" />
      </g>
      <g clipPath="url(#c)">
        <path d="m12.3 70.86v-69.855" fill="none" stroke="#7f7f7f" strokeMiterlimit="10" strokeWidth="2.04" />
      </g>
      <g clipPath="url(#b)">
        <path d="m61.62 70.86v-69.855" fill="none" stroke="#7f7f7f" strokeMiterlimit="10" strokeWidth="2.04" />
      </g>
      <g clipPath="url(#a)">
        <path d="m12.3 69.78" fill="none" stroke="#7f7f7f" strokeMiterlimit="10" strokeWidth="2.04" />
      </g>
      <path d="m11.58 70.14 51.024-0.146" fill="none" stroke="#7f7f7f" strokeMiterlimit="10" strokeWidth="2.04" />
      <path d="M 13.02,2.0454 37.782,15.9" fill="none" stroke="#7f7f7f" strokeMiterlimit="10" strokeWidth="2.04" />
      <path d="M 61.508,2.0453 37.02,15.9" fill="none" stroke="#7f7f7f" strokeMiterlimit="10" strokeWidth="2.04" />
    </g>
  </svg>
);

export const ShareIcon = () => (
  <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path
      d="M16 4a3 3 0 11-2.064 5.177l-3.997 2.22a3.014 3.014 0 010 1.207l3.997 2.22a3 3 0 11-.875 1.574l-3.997-2.221a3 3 0 110-4.354l3.997-2.22A3 3 0 0116 4zm1 12h-2v2h2v-2zm-9-5H6v2h2v-2zm9-5h-2v2h2V6z"
      fillRule="nonzero"
    ></path>
  </svg>
);

export const ShareIcon2 = () => (
  <svg version="1.1" viewBox="0 0 96 96" xmlSpace="preserve" xmlns="http://www.w3.org/2000/svg">
    <defs>
      <clipPath id="b">
        <path d="m0 2.2888e-5h72v72h-72z" clipRule="evenodd" />
      </clipPath>
      <clipPath id="a">
        <path d="m-4.77e-7 72h72v-72l-72 7.629e-6" clipRule="evenodd" />
      </clipPath>
    </defs>
    <g transform="matrix(1.3333 0 0 -1.3333 0 96)">
      <g clipPath="url(#b)">
        <path d="m0 7.629e-6h72v72h-72z" fill="#fff" fillRule="evenodd" />
      </g>
      <g clipPath="url(#a)">
        <path
          d="m4.38 1.6799c0 25.814 27.803 46.74 62.1 46.74 0.53 0 1.06-5e-3 1.59-0.015"
          fill="none"
          stroke="#000"
          strokeMiterlimit="10"
          strokeWidth="2.04"
        />
      </g>
      <path d="m48.42 68.7 19.261-20.479" fill="none" stroke="#878A8C" strokeMiterlimit="10" strokeWidth="5" />
      <path d="m68.04 49.26-18.78-22.07" fill="none" stroke="#878A8C" strokeMiterlimit="10" strokeWidth="5" />
    </g>
  </svg>
);

export const ShareIcon3 = () => (
  <svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="#878a8c">
    <path d="M19 11v5.378A2.625 2.625 0 0116.378 19H3.622A2.625 2.625 0 011 16.378V11h1.25v5.378a1.373 1.373 0 001.372 1.372h12.756a1.373 1.373 0 001.372-1.372V11H19zM9.375 3.009V14h1.25V3.009l2.933 2.933.884-.884-4-4a.624.624 0 00-.884 0l-4 4 .884.884 2.933-2.933z"></path>
  </svg>
);

export const UrlShareIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    className="icon icon-tabler icon-tabler-link"
    width="100%"
    height="100%"
    viewBox="0 0 24 24"
    strokeWidth="2"
    stroke="currentColor"
    fill="none"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
    <path d="M10 14a3.5 3.5 0 0 0 5 0l4 -4a3.5 3.5 0 0 0 -5 -5l-.5 .5"></path>
    <path d="M14 10a3.5 3.5 0 0 0 -5 0l-4 4a3.5 3.5 0 0 0 5 5l.5 -.5"></path>
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

export const CalendarIcon1 = () => (
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="#4096ff">
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
    ></path>
  </svg>
);

export const CalendarIcon2 = () => (
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="#4096ff">
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4"
    ></path>
  </svg>
);

export const HomeIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="#4096ff">
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
    ></path>
  </svg>
);

export const CardIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="gray" width="1rem" height="1rem">
    <path d="M22 3c.53 0 1.039.211 1.414.586s.586.884.586 1.414v14c0 .53-.211 1.039-.586 1.414s-.884.586-1.414.586h-20c-.53 0-1.039-.211-1.414-.586s-.586-.884-.586-1.414v-14c0-.53.211-1.039.586-1.414s.884-.586 1.414-.586h20zm1 8h-22v8c0 .552.448 1 1 1h20c.552 0 1-.448 1-1v-8zm-15 5v1h-5v-1h5zm13-2v1h-3v-1h3zm-10 0v1h-8v-1h8zm-10-6v2h22v-2h-22zm22-1v-2c0-.552-.448-1-1-1h-20c-.552 0-1 .448-1 1v2h22z" />
  </svg>
);

export const HotIcon = () => (
  <svg width="27" height="27" xmlns="http://www.w3.org/2000/svg">
    <image
      preserveAspectRatio="none"
      xlinkHref="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABsAAAAbCAYAAACN1PRVAAABXWlDQ1BJQ0MgUHJvZmlsZQAAKJFt kD1LQgEUhh/TEkpIoiGiwcElsgi1liZziEBKTPsaouvVNFC7XJVo7w+0RFvQUAQ1lqvRDwgqLJqD fkAgRMntXK3U6sDhfXg553B4ocOpaFrGBmRzBT0yM+1aXll12V/owkofbiyKmtcC4XBIRvjW9qpW sJh6N2reur0+f8w6Knu+0NqRcnrl/jvfVt2JZF4V/ZCeVDW9ABa/cHi7oJm8K9yvy1PCByanGnxm crzB5fpMNBIUvhd2qmklIfws7Im3+KkWzmaK6tcP5veOZC62IDooPUSMEFEizDOHCy9+fEywyIjk 9P+ev74XZAuNHXQ2SZGmINsBcTQyJIVnyaEyhqd+dVzaZ+b9O8emV1Rg6g2sN01vvQIXXTBQanqu Q+iV7csTTdGVn3QtVVt+w+dtcE8JOvcN43UJ7MNQezCM95Jh1I7l/hOUq59zx2VfEKyAeQAAAFZl WElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA5KGAAcAAAASAAAARKACAAQAAAABAAAAG6AD AAQAAAABAAAAGwAAAABBU0NJSQAAAFNjcmVlbnNob3QAw7XmAAAB1GlUWHRYTUw6Y29tLmFkb2Jl LnhtcAAAAAAAPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iWE1Q IENvcmUgNi4wLjAiPgogICA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5 OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgogICAgICA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91 dD0iIgogICAgICAgICAgICB4bWxuczpleGlmPSJodHRwOi8vbnMuYWRvYmUuY29tL2V4aWYvMS4w LyI+CiAgICAgICAgIDxleGlmOlBpeGVsWURpbWVuc2lvbj4yNzwvZXhpZjpQaXhlbFlEaW1lbnNp b24+CiAgICAgICAgIDxleGlmOlBpeGVsWERpbWVuc2lvbj4yNzwvZXhpZjpQaXhlbFhEaW1lbnNp b24+CiAgICAgICAgIDxleGlmOlVzZXJDb21tZW50PlNjcmVlbnNob3Q8L2V4aWY6VXNlckNvbW1l bnQ+CiAgICAgIDwvcmRmOkRlc2NyaXB0aW9uPgogICA8L3JkZjpSREY+CjwveDp4bXBtZXRhPgp6 KXuvAAADd0lEQVRIDe1VWU9TQRT+WlmERiBAbNkXg4ILYBQrEBUQEHCP4kYEgv4cE+Mf0KDIJsga REBwIRGBupBYQtmKyFpsBaWlLaIzg/faW7i0vPDkebhz5syZ883c850zkt9EsEUi3SIcBvMfTPC3 zWYzWtteYGRkVGB3NJFsliADGg3qG5sgkUjw48cC4uPikJWZCQ+P7Y6w4DQYJW1zSyvednUhUalE RvpJTE5NobqmDsvLy8jPuwG5fOeGgE6BWa3LqHpag1HtKK7l5iIyMoIParFYUdfQgAHNIK5fycWu XZH8mr3iEIxWYXllJca/TqDgZh7kO9eenvo0NTejV6XCrcJCBAcH2eOwuUPqt3d0YJgQoaggf10g GoWkD6ezsxAXG4vH5RVYWFjYPNjMzCxevenEpYsX4O/vt24AW+OZnBx4e3mRW7bYmnl9w5t1vHqN 3VFRiInew2/gFJPJxKn86OKyDWdP50Dd34+Z2VnezimiYNRZ3a9GyvFjnC8/fvzUhzt376GktAxG oxA0KCiQkeQlOai9iIJRdgUGBq5Jdk+vitC9FsqEw/j2TY/7xcUwmy2CuInKI4SdGtj3eFEwnU6H AIVCEOSzWo3GpiZkZ51CZkY6bhcVQiqRouJJFVZW/j0eCrkCtFy+f58X7BcHm5sjpPDnnWmwZ89b kJyUhKSjSmaXyWQozL+JickJvO7s5H29vHbA3d0Nujkdb6OKKBjIQW1/w4BmAIuLRiQnJgoCeHp6 IC0lBV3vukkn+cXWaN1R4cbV2QZgfn6+mJ6e5vzQ3aPCwfg4yGSevI1TaH80GhdJ4Y8zk8FgYHn0 9xOWi+jN9sZEMwrT21DRG/QIDQlhuv2H/jIfHx/o9Qa21EM6Ce00vr6+AtcNwGJYgNr6BtZoXV1c yelXgQUR/k5oj6S5GhoaxrvubpwgJUM7i62IgtEn5GruZWjHtHjw8BHCw8Ogev9hTR5osFGtFhaL hTw5P1FSVo601FQc2L/PFofpDhvx2NgXVFZXs2BLS2YkHD4E2pak0tVj02emtLyS3ZoS6uL5c4g9 sH8NEDU4BKNOJtMS2trb8amvjyWekiQ0JBSLlBTjXxlrAwIUyCaPaEREONmxvjgFxm21Wq3QDA6y zjFPOru7mxu8vb0RFhYKhVzOuYmOmwITjeLkgihBnNy/KbctBfsDhcpwmwgez/EAAAAASUVORK5C YII="
      x=".114"
      y=".229"
    />
  </svg>
);

export const NewIcon = () => (
  <svg width="27" height="28" xmlns="http://www.w3.org/2000/svg">
    <image
      preserveAspectRatio="none"
      xlinkHref="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABsAAAAcCAYAAACQ0cTtAAABXWlDQ1BJQ0MgUHJvZmlsZQAAKJFt kD1LQgEUhh/TEkpIoiGiwcElsgi1liZziEBKTPsaouvVNFC7XJVo7w+0RFvQUAQ1lqvRDwgqLJqD fkAgRMntXK3U6sDhfXg553B4ocOpaFrGBmRzBT0yM+1aXll12V/owkofbiyKmtcC4XBIRvjW9qpW sJh6N2reur0+f8w6Knu+0NqRcnrl/jvfVt2JZF4V/ZCeVDW9ABa/cHi7oJm8K9yvy1PCByanGnxm crzB5fpMNBIUvhd2qmklIfws7Im3+KkWzmaK6tcP5veOZC62IDooPUSMEFEizDOHCy9+fEywyIjk 9P+ev74XZAuNHXQ2SZGmINsBcTQyJIVnyaEyhqd+dVzaZ+b9O8emV1Rg6g2sN01vvQIXXTBQanqu Q+iV7csTTdGVn3QtVVt+w+dtcE8JOvcN43UJ7MNQezCM95Jh1I7l/hOUq59zx2VfEKyAeQAAAFZl WElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA5KGAAcAAAASAAAARKACAAQAAAABAAAAG6AD AAQAAAABAAAAHAAAAABBU0NJSQAAAFNjcmVlbnNob3R2ei1IAAAB1GlUWHRYTUw6Y29tLmFkb2Jl LnhtcAAAAAAAPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iWE1Q IENvcmUgNi4wLjAiPgogICA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5 OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgogICAgICA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91 dD0iIgogICAgICAgICAgICB4bWxuczpleGlmPSJodHRwOi8vbnMuYWRvYmUuY29tL2V4aWYvMS4w LyI+CiAgICAgICAgIDxleGlmOlBpeGVsWURpbWVuc2lvbj4yODwvZXhpZjpQaXhlbFlEaW1lbnNp b24+CiAgICAgICAgIDxleGlmOlBpeGVsWERpbWVuc2lvbj4yNzwvZXhpZjpQaXhlbFhEaW1lbnNp b24+CiAgICAgICAgIDxleGlmOlVzZXJDb21tZW50PlNjcmVlbnNob3Q8L2V4aWY6VXNlckNvbW1l bnQ+CiAgICAgIDwvcmRmOkRlc2NyaXB0aW9uPgogICA8L3JkZjpSREY+CjwveDp4bXBtZXRhPgpC mD69AAADG0lEQVRIDe1V3UuTURj/zWnbaG5OBd3msBs3C9R0miYlkRhp0+GNhNEHXRf0BwTRdSB9 YJK3lVYUYaaVRZSKH2UZWWmJNhH3gW4uMXGb29s5bzocnvfVjfCq52Z7z/M7z+95nvOc35FwxLBN FrdNPDzNf7J/0u2Y2mizTeHN226srKxElUR8VGgCXlpawp3WewgGgwgQsoryw1sOsSlZe0cnRr58 RaG5AKUlxeju6YVapULZwQNoa29HcVEhHE4nenr74Jn34OyZ00hNSWEmsCmZbWoKer0Oo2Nj6B8Y BMeFcLyuDiajEX0DA7jeeJNvZ15uLmbsM3C5XLGTaZKSkJycjFMn6vFjfJyQAdkmI5/5yfp6Pglj VhZksh34OPwJSQQvZKKV0QGQyWTwen9BIpHw1awPpFTuRFGhmV9yOl38r1QqXQ+J+M8k6x98h/dD Q3C73XwlxyqPRmxifWg0GtIBDRqbbkGhkCNDnwFrtQVqtSoMl7C08UrDVagSE3Gkohza9HTI5fLw BrE/oRCHubk5jE9M4EVXFyGrhrkgP7yFec9oJXaHg2x0b5mIRoyLk0CpVGKYnJ3BYEBuTk6YiPdH fK1+7NmdjVprDZ52dsJud7AggmuP256AnhsdnoSEyFNiViYYaQsOejWEjEn2bXQMNENLVRV0Oq3Q XuZ6rdVKlCWA2y0tCAQi5YxJ1vHsOXRaLVJTU7C8vMwMylqk7zDFF5nNmJ6exueRkQgYcxqpUrwf +gC35+/oW6oqsY/Ikpj5fH40NTeT6+Iho6+AIUOPGkvk6JN7JGx+f4B78PARd7f1vjBo1eNwOLmL ly5zRCcFscw2rlVAp8nn8xEJUtOkiDR950V5zb+4+JvvwMLCAo+h6/Q1ELLI2WSg5r1eBEMhXnCp bNFpo0mYjCZ+CKjK0DPO35uH+HgpL216nY4RCdiUbFdm5oYn5uWr16RiP2ZnZ3Hh/LnwEyOXK5Ce lsYkoovMARFEEwd9PBuu3eDbVbq/JKrHM2oymsjE5CQmf9pwqKxsg0qIJRoTmVhAMZ/oNIptjMW3 rWR/AFMfklYOK4mnAAAAAElFTkSuQmCC"
    />
  </svg>
);

export const UpvoteIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
    <g clipPath="url(#clip0_472_1110)">
      <path d="M12.877 19H7.123A1.125 1.125 0 016 17.877V11H2.126a1.114 1.114 0 01-1.007-.7 1.249 1.249 0 01.171-1.343L9.166.368a1.128 1.128 0 011.668.004l7.872 8.581a1.252 1.252 0 01.176 1.348 1.114 1.114 0 01-1.005.7H14v6.877A1.125 1.125 0 0112.877 19zM7.25 17.75h5.5v-8h4.934L10 1.31 2.258 9.75H7.25v8zM2.227 9.784l-.012.016c.01-.006.014-.01.012-.016z"></path>
    </g>
  </svg>
);

export const DownvoteIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
    <g clipPath="url(#clip0_472_1137)">
      <path d="M10 20a1.122 1.122 0 01-.834-.372l-7.872-8.581A1.251 1.251 0 011.118 9.7 1.114 1.114 0 012.123 9H6V2.123A1.125 1.125 0 017.123 1h5.754A1.125 1.125 0 0114 2.123V9h3.874a1.114 1.114 0 011.007.7 1.25 1.25 0 01-.171 1.345l-7.876 8.589A1.128 1.128 0 0110 20zm-7.684-9.75L10 18.69l7.74-8.44h-4.99v-8h-5.5v8H2.316zm15.469-.05c-.01 0-.014.007-.012.013l.012-.013z"></path>
    </g>
  </svg>
);

export const UpvoteFilledIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="#d93a00">
    <g clipPath="url(#clip0_472_1141)">
      <path d="M18.706 8.953L10.834.372A1.123 1.123 0 0010 0a1.128 1.128 0 00-.833.368L1.29 8.957a1.249 1.249 0 00-.17 1.343 1.114 1.114 0 001.006.7H6v6.877A1.125 1.125 0 007.123 19h5.754A1.125 1.125 0 0014 17.877V11h3.877a1.114 1.114 0 001.005-.7 1.25 1.25 0 00-.176-1.347z"></path>
    </g>
  </svg>
);

export const DownvoteFilledIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="#6a5cff">
    <path d="M18.88 9.7a1.114 1.114 0 00-1.006-.7H14V2.123A1.125 1.125 0 0012.877 1H7.123A1.125 1.125 0 006 2.123V9H2.123a1.114 1.114 0 00-1.005.7 1.25 1.25 0 00.176 1.348l7.872 8.581a1.124 1.124 0 001.667.003l7.876-8.589A1.248 1.248 0 0018.88 9.7z"></path>
  </svg>
);

export const UpvoteIcon2 = () => (
  <svg
    version="1.1"
    viewBox="0 0 96 118.72"
    width="100%"
    height="100%"
    xmlSpace="preserve"
    xmlns="http://www.w3.org/2000/svg"
  >
    <defs>
      <clipPath id="a">
        <path d="m0 2.2888e-5h72v89.04h-72z" clipRule="evenodd" />
      </clipPath>
    </defs>
    <g transform="matrix(1.3333 0 0 -1.3333 0 118.72)">
      <g clipPath="url(#a)">
        <path d="m0 7.629e-6h72v89.04h-72z" fill="#fff" fillRule="evenodd" />
      </g>
      <path
        d="m1.08 43.397 34.92 44.683 34.92-44.683h-22.216v-41.837h-25.408v41.837z"
        fill="none"
        stroke="#878A8C"
        strokeMiterlimit="10"
        strokeWidth="5"
      />
    </g>
  </svg>
);

export const DownvoteIcon2 = () => (
  <svg
    version="1.1"
    viewBox="0 0 96 118.72"
    width="100%"
    height="100%"
    xmlSpace="preserve"
    xmlns="http://www.w3.org/2000/svg"
  >
    <defs>
      <clipPath id="a">
        <path d="m0 2.2888e-5h72v89.04h-72z" clipRule="evenodd" />
      </clipPath>
    </defs>
    <g transform="matrix(1.3333 0 0 -1.3333 0 118.72)">
      <g clipPath="url(#a)">
        <path d="m0 7.629e-6h72v89.04h-72z" fill="#fff" fillRule="evenodd" />
      </g>
      <path
        d="m70.92 46.243-34.92-44.683-34.92 44.683h22.216v41.837h25.408v-41.837z"
        fill="none"
        stroke="#878A8C"
        strokeMiterlimit="10"
        strokeWidth="5"
      />
    </g>
  </svg>
);

export const CommentIcon = () => (
  <svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
    <path d="M7.725 19.872a.718.718 0 01-.607-.328.725.725 0 01-.118-.397V16H3.625A2.63 2.63 0 011 13.375v-9.75A2.629 2.629 0 013.625 1h12.75A2.63 2.63 0 0119 3.625v9.75A2.63 2.63 0 0116.375 16h-4.161l-4 3.681a.725.725 0 01-.489.191zM3.625 2.25A1.377 1.377 0 002.25 3.625v9.75a1.377 1.377 0 001.375 1.375h4a.625.625 0 01.625.625v2.575l3.3-3.035a.628.628 0 01.424-.165h4.4a1.377 1.377 0 001.375-1.375v-9.75a1.377 1.377 0 00-1.374-1.375H3.625z"></path>
  </svg>
);

export const ThreeDotsIcon1 = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
    <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zm4-2a2 2 0 100 4 2 2 0 000-4zm6 0a2 2 0 100 4 2 2 0 000-4z"></path>
  </svg>
);

export const ThreeDotsIcon2 = () => (
  <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
    <path d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z" />
  </svg>
);

export const AddIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" height="20" width="20" fill="currentColor">
    <path d="M19 9.375h-8.375V1h-1.25v8.375H1v1.25h8.375V19h1.25v-8.375H19v-1.25z"></path>
  </svg>
);

export const PenIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
    <path d="M21.731 2.269a2.625 2.625 0 00-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 000-3.712zM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 00-1.32 2.214l-.8 2.685a.75.75 0 00.933.933l2.685-.8a5.25 5.25 0 002.214-1.32L19.513 8.2z"></path>
  </svg>
);

export const SearchIcon = () => (
  <svg viewBox="64 64 896 896" focusable="false" fill="currentColor" aria-hidden="true">
    <path d="M909.6 854.5L649.9 594.8C690.2 542.7 712 479 712 412c0-80.2-31.3-155.4-87.9-212.1-56.6-56.7-132-87.9-212.1-87.9s-155.5 31.3-212.1 87.9C143.2 256.5 112 331.8 112 412c0 80.1 31.3 155.5 87.9 212.1C256.5 680.8 331.8 712 412 712c67 0 130.6-21.8 182.7-62l259.7 259.6a8.2 8.2 0 0011.6 0l43.6-43.5a8.2 8.2 0 000-11.6zM570.4 570.4C528 612.7 471.8 636 412 636s-116-23.3-158.4-65.6C211.3 528 188 471.8 188 412s23.3-116.1 65.6-158.4C296 211.3 352.2 188 412 188s116.1 23.2 158.4 65.6S636 352.2 636 412s-23.3 116.1-65.6 158.4z"></path>
  </svg>
);

export const FireIcon = () => (
  <svg aria-hidden="true" focusable="false" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
    <path d="M159.3 5.4c7.8-7.3 19.9-7.2 27.7 .1c27.6 25.9 53.5 53.8 77.7 84c11-14.4 23.5-30.1 37-42.9c7.9-7.4 20.1-7.4 28 .1c34.6 33 63.9 76.6 84.5 118c20.3 40.8 33.8 82.5 33.8 111.9C448 404.2 348.2 512 224 512C98.4 512 0 404.1 0 276.5c0-38.4 17.8-85.3 45.4-131.7C73.3 97.7 112.7 48.6 159.3 5.4zM225.7 416c25.3 0 47.7-7 68.8-21c42.1-29.4 53.4-88.2 28.1-134.4c-4.5-9-16-9.6-22.5-2l-25.2 29.3c-6.6 7.6-18.5 7.4-24.7-.5c-16.5-21-46-58.5-62.8-79.8c-6.3-8-18.3-8.1-24.7-.1c-33.8 42.5-50.8 69.3-50.8 99.4C112 375.4 162.6 416 225.7 416z"></path>
  </svg>
);

export const FireIconBlue = () => (
  <svg
    fill="rgb(0, 121, 211)"
    aria-hidden="true"
    focusable="false"
    role="img"
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 448 512"
  >
    <path d="M159.3 5.4c7.8-7.3 19.9-7.2 27.7 .1c27.6 25.9 53.5 53.8 77.7 84c11-14.4 23.5-30.1 37-42.9c7.9-7.4 20.1-7.4 28 .1c34.6 33 63.9 76.6 84.5 118c20.3 40.8 33.8 82.5 33.8 111.9C448 404.2 348.2 512 224 512C98.4 512 0 404.1 0 276.5c0-38.4 17.8-85.3 45.4-131.7C73.3 97.7 112.7 48.6 159.3 5.4zM225.7 416c25.3 0 47.7-7 68.8-21c42.1-29.4 53.4-88.2 28.1-134.4c-4.5-9-16-9.6-22.5-2l-25.2 29.3c-6.6 7.6-18.5 7.4-24.7-.5c-16.5-21-46-58.5-62.8-79.8c-6.3-8-18.3-8.1-24.7-.1c-33.8 42.5-50.8 69.3-50.8 99.4C112 375.4 162.6 416 225.7 416z"></path>
  </svg>
);

// https://www.svgrepo.com/svg/458456/fire-1
export const FireIcon2 = () => (
  <svg viewBox="3 3 18 18" xmlns="http://www.w3.org/2000/svg">
    <g strokeWidth="0"></g>
    <g strokeLinecap="round" strokeLinejoin="round"></g>
    <g>
      <path
        d="M18.0052 16.4884L17.5433 16.297L18.0052 16.4884ZM16.5962 18.5971L16.9497 18.9507H16.9497L16.5962 18.5971ZM14.4874 20.0061L14.2961 19.5442L14.2961 19.5442L14.4874 20.0061ZM9.51256 20.0061L9.7039 19.5442H9.7039L9.51256 20.0061ZM7.40381 18.5971L7.75736 18.2436H7.75736L7.40381 18.5971ZM5.99478 16.4884L5.53284 16.6797H5.53284L5.99478 16.4884ZM15.4072 6.58312L15.0664 6.21724L15.4072 6.58312ZM15.5914 6.58184L15.9321 6.21583L15.5914 6.58184ZM14.3536 7.48159L14.7629 7.19445L14.3536 7.48159ZM14.6027 7.48614L14.9998 7.7899L14.6027 7.48614ZM11.4202 4.54841L11.6837 4.97335L11.4202 4.54841ZM11.577 4.5562L11.2775 4.95663L11.577 4.5562ZM11.1567 4.12347C10.7792 4.35757 9.25266 5.35391 7.81554 7.01613C6.37866 8.67808 5 11.043 5 14.0009H6C6 11.3677 7.22635 9.2266 8.57201 7.67016C9.91745 6.11398 11.351 5.1796 11.6837 4.97335L11.1567 4.12347ZM14.7629 7.19445C13.5479 5.46236 12.2233 4.4152 11.8764 4.15577L11.2775 4.95663C11.5755 5.17945 12.8113 6.15362 13.9443 7.76873L14.7629 7.19445ZM14.9998 7.7899C15.3371 7.34891 15.6197 7.06843 15.748 6.94899L15.0664 6.21724C14.9037 6.36877 14.5819 6.69032 14.2055 7.18238L14.9998 7.7899ZM15.2508 6.94784C15.6999 7.36582 18 9.71583 18 13.9999H19C19 9.3298 16.4885 6.73372 15.9321 6.21583L15.2508 6.94784ZM18 13.9999V14.0009H19V13.9999H18ZM18 14.0009C18 14.7888 17.8448 15.5691 17.5433 16.297L18.4672 16.6797C18.8189 15.8304 19 14.9202 19 14.0009H18ZM17.5433 16.297C17.2417 17.025 16.7998 17.6864 16.2426 18.2436L16.9497 18.9507C17.5998 18.3007 18.1154 17.529 18.4672 16.6797L17.5433 16.297ZM16.2426 18.2436C15.6855 18.8007 15.0241 19.2427 14.2961 19.5442L14.6788 20.4681C15.5281 20.1163 16.2997 19.6007 16.9497 18.9507L16.2426 18.2436ZM14.2961 19.5442C13.5681 19.8457 12.7879 20.0009 12 20.0009V21.0009C12.9193 21.0009 13.8295 20.8199 14.6788 20.4681L14.2961 19.5442ZM12 20.0009C11.2121 20.0009 10.4319 19.8457 9.7039 19.5442L9.32122 20.4681C10.1705 20.8199 11.0807 21.0009 12 21.0009V20.0009ZM9.7039 19.5442C8.97595 19.2427 8.31451 18.8007 7.75736 18.2436L7.05025 18.9507C7.70026 19.6007 8.47194 20.1163 9.32122 20.4681L9.7039 19.5442ZM7.75736 18.2436C7.20021 17.6864 6.75825 17.025 6.45672 16.297L5.53284 16.6797C5.88463 17.529 6.40024 18.3007 7.05025 18.9507L7.75736 18.2436ZM6.45672 16.297C6.15519 15.5691 6 14.7888 6 14.0009H5C5 14.9202 5.18106 15.8304 5.53284 16.6797L6.45672 16.297ZM15.748 6.94899C15.6154 7.07251 15.3961 7.08306 15.2508 6.94784L15.9321 6.21583C15.6808 5.98195 15.305 5.995 15.0664 6.21724L15.748 6.94899ZM13.9443 7.76873C14.2 8.13332 14.7335 8.13813 14.9998 7.7899L14.2055 7.18238C14.3456 6.99919 14.6265 6.99994 14.7629 7.19445L13.9443 7.76873ZM11.6837 4.97335C11.5526 5.05465 11.3896 5.04043 11.2775 4.95663L11.8764 4.15577C11.6706 4.00186 11.3873 3.98046 11.1567 4.12347L11.6837 4.97335Z"
        fill="#222222"
      ></path>
      <path
        d="M15.4991 16.9218L14.9992 16.932L14.9993 16.9328L15.4991 16.9218ZM8.5 17.0004L9 17.0004L8.5 17.0004ZM12.0859 11.5642L12.388 11.1658L12.0859 11.5642ZM11.6119 11.1658C11.3275 11.3815 10.4392 12.0883 9.61737 13.1C8.80358 14.1019 7.99995 15.4704 7.99994 17.0004L8.99994 17.0004C8.99995 15.8106 9.63467 14.6648 10.3936 13.7305C11.1445 12.806 11.9632 12.1545 12.2162 11.9626L11.6119 11.1658ZM15.999 16.9117C15.9684 15.4034 15.1657 14.0571 14.3586 13.0706C13.5437 12.0747 12.6698 11.3795 12.388 11.1658L11.7837 11.9626C12.0342 12.1525 12.8396 12.7934 13.5847 13.7039C14.3375 14.624 14.9754 15.7547 14.9992 16.932L15.999 16.9117ZM14.9993 16.9328C14.9998 16.9552 15 16.9776 15 17.0001H16C16 16.9703 15.9997 16.9405 15.999 16.9109L14.9993 16.9328ZM15 17.0001C15 18.657 13.6569 20.0001 12 20.0001V21.0001C14.2091 21.0001 16 19.2093 16 17.0001H15ZM12 20.0001C10.3432 20.0001 9.00014 18.6571 9 17.0004L8 17.0005C8.00019 19.2095 9.79098 21.0001 12 21.0001V20.0001ZM7.99994 17.0004C7.99994 17.2754 8.22267 17.5004 8.49997 17.5004V16.5004C8.77723 16.5004 8.99994 16.7254 8.99994 17.0004L7.99994 17.0004ZM9 17.0004C8.99998 16.7293 8.78117 16.5004 8.49997 16.5004V17.5004C8.2188 17.5004 8.00002 17.2715 8 17.0005L9 17.0004ZM12.2162 11.9626C12.0888 12.0593 11.9114 12.0594 11.7837 11.9626L12.388 11.1658C12.1582 10.9915 11.8415 10.9917 11.6119 11.1658L12.2162 11.9626Z"
        fill="#222222"
      ></path>
    </g>
  </svg>
);

export const FireIcon2Blue = () => (
  <svg fill="rgb(0, 121, 211)" viewBox="3 3 18 18" xmlns="http://www.w3.org/2000/svg">
    <g strokeWidth="0"></g>
    <g strokeLinecap="round" strokeLinejoin="round"></g>
    <g>
      <path
        d="M18.0052 16.4884L17.5433 16.297L18.0052 16.4884ZM16.5962 18.5971L16.9497 18.9507H16.9497L16.5962 18.5971ZM14.4874 20.0061L14.2961 19.5442L14.2961 19.5442L14.4874 20.0061ZM9.51256 20.0061L9.7039 19.5442H9.7039L9.51256 20.0061ZM7.40381 18.5971L7.75736 18.2436H7.75736L7.40381 18.5971ZM5.99478 16.4884L5.53284 16.6797H5.53284L5.99478 16.4884ZM15.4072 6.58312L15.0664 6.21724L15.4072 6.58312ZM15.5914 6.58184L15.9321 6.21583L15.5914 6.58184ZM14.3536 7.48159L14.7629 7.19445L14.3536 7.48159ZM14.6027 7.48614L14.9998 7.7899L14.6027 7.48614ZM11.4202 4.54841L11.6837 4.97335L11.4202 4.54841ZM11.577 4.5562L11.2775 4.95663L11.577 4.5562ZM11.1567 4.12347C10.7792 4.35757 9.25266 5.35391 7.81554 7.01613C6.37866 8.67808 5 11.043 5 14.0009H6C6 11.3677 7.22635 9.2266 8.57201 7.67016C9.91745 6.11398 11.351 5.1796 11.6837 4.97335L11.1567 4.12347ZM14.7629 7.19445C13.5479 5.46236 12.2233 4.4152 11.8764 4.15577L11.2775 4.95663C11.5755 5.17945 12.8113 6.15362 13.9443 7.76873L14.7629 7.19445ZM14.9998 7.7899C15.3371 7.34891 15.6197 7.06843 15.748 6.94899L15.0664 6.21724C14.9037 6.36877 14.5819 6.69032 14.2055 7.18238L14.9998 7.7899ZM15.2508 6.94784C15.6999 7.36582 18 9.71583 18 13.9999H19C19 9.3298 16.4885 6.73372 15.9321 6.21583L15.2508 6.94784ZM18 13.9999V14.0009H19V13.9999H18ZM18 14.0009C18 14.7888 17.8448 15.5691 17.5433 16.297L18.4672 16.6797C18.8189 15.8304 19 14.9202 19 14.0009H18ZM17.5433 16.297C17.2417 17.025 16.7998 17.6864 16.2426 18.2436L16.9497 18.9507C17.5998 18.3007 18.1154 17.529 18.4672 16.6797L17.5433 16.297ZM16.2426 18.2436C15.6855 18.8007 15.0241 19.2427 14.2961 19.5442L14.6788 20.4681C15.5281 20.1163 16.2997 19.6007 16.9497 18.9507L16.2426 18.2436ZM14.2961 19.5442C13.5681 19.8457 12.7879 20.0009 12 20.0009V21.0009C12.9193 21.0009 13.8295 20.8199 14.6788 20.4681L14.2961 19.5442ZM12 20.0009C11.2121 20.0009 10.4319 19.8457 9.7039 19.5442L9.32122 20.4681C10.1705 20.8199 11.0807 21.0009 12 21.0009V20.0009ZM9.7039 19.5442C8.97595 19.2427 8.31451 18.8007 7.75736 18.2436L7.05025 18.9507C7.70026 19.6007 8.47194 20.1163 9.32122 20.4681L9.7039 19.5442ZM7.75736 18.2436C7.20021 17.6864 6.75825 17.025 6.45672 16.297L5.53284 16.6797C5.88463 17.529 6.40024 18.3007 7.05025 18.9507L7.75736 18.2436ZM6.45672 16.297C6.15519 15.5691 6 14.7888 6 14.0009H5C5 14.9202 5.18106 15.8304 5.53284 16.6797L6.45672 16.297ZM15.748 6.94899C15.6154 7.07251 15.3961 7.08306 15.2508 6.94784L15.9321 6.21583C15.6808 5.98195 15.305 5.995 15.0664 6.21724L15.748 6.94899ZM13.9443 7.76873C14.2 8.13332 14.7335 8.13813 14.9998 7.7899L14.2055 7.18238C14.3456 6.99919 14.6265 6.99994 14.7629 7.19445L13.9443 7.76873ZM11.6837 4.97335C11.5526 5.05465 11.3896 5.04043 11.2775 4.95663L11.8764 4.15577C11.6706 4.00186 11.3873 3.98046 11.1567 4.12347L11.6837 4.97335Z"
        fill="rgb(0, 121, 211)"
        stroke="rgb(0, 121, 211)"
      ></path>
      <path
        d="M15.4991 16.9218L14.9992 16.932L14.9993 16.9328L15.4991 16.9218ZM8.5 17.0004L9 17.0004L8.5 17.0004ZM12.0859 11.5642L12.388 11.1658L12.0859 11.5642ZM11.6119 11.1658C11.3275 11.3815 10.4392 12.0883 9.61737 13.1C8.80358 14.1019 7.99995 15.4704 7.99994 17.0004L8.99994 17.0004C8.99995 15.8106 9.63467 14.6648 10.3936 13.7305C11.1445 12.806 11.9632 12.1545 12.2162 11.9626L11.6119 11.1658ZM15.999 16.9117C15.9684 15.4034 15.1657 14.0571 14.3586 13.0706C13.5437 12.0747 12.6698 11.3795 12.388 11.1658L11.7837 11.9626C12.0342 12.1525 12.8396 12.7934 13.5847 13.7039C14.3375 14.624 14.9754 15.7547 14.9992 16.932L15.999 16.9117ZM14.9993 16.9328C14.9998 16.9552 15 16.9776 15 17.0001H16C16 16.9703 15.9997 16.9405 15.999 16.9109L14.9993 16.9328ZM15 17.0001C15 18.657 13.6569 20.0001 12 20.0001V21.0001C14.2091 21.0001 16 19.2093 16 17.0001H15ZM12 20.0001C10.3432 20.0001 9.00014 18.6571 9 17.0004L8 17.0005C8.00019 19.2095 9.79098 21.0001 12 21.0001V20.0001ZM7.99994 17.0004C7.99994 17.2754 8.22267 17.5004 8.49997 17.5004V16.5004C8.77723 16.5004 8.99994 16.7254 8.99994 17.0004L7.99994 17.0004ZM9 17.0004C8.99998 16.7293 8.78117 16.5004 8.49997 16.5004V17.5004C8.2188 17.5004 8.00002 17.2715 8 17.0005L9 17.0004ZM12.2162 11.9626C12.0888 12.0593 11.9114 12.0594 11.7837 11.9626L12.388 11.1658C12.1582 10.9915 11.8415 10.9917 11.6119 11.1658L12.2162 11.9626Z"
        fill="rgb(0, 121, 211)"
        stroke="rgb(0, 121, 211)"
      ></path>
    </g>
  </svg>
);

export const BulbIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
    <path d="M297.2 248.9C311.6 228.3 320 203.2 320 176c0-70.7-57.3-128-128-128S64 105.3 64 176c0 27.2 8.4 52.3 22.8 72.9c3.7 5.3 8.1 11.3 12.8 17.7l0 0c12.9 17.7 28.3 38.9 39.8 59.8c10.4 19 15.7 38.8 18.3 57.5H109c-2.2-12-5.9-23.7-11.8-34.5c-9.9-18-22.2-34.9-34.5-51.8l0 0 0 0c-5.2-7.1-10.4-14.2-15.4-21.4C27.6 247.9 16 213.3 16 176C16 78.8 94.8 0 192 0s176 78.8 176 176c0 37.3-11.6 71.9-31.4 100.3c-5 7.2-10.2 14.3-15.4 21.4l0 0 0 0c-12.3 16.8-24.6 33.7-34.5 51.8c-5.9 10.8-9.6 22.5-11.8 34.5H226.4c2.6-18.7 7.9-38.6 18.3-57.5c11.5-20.9 26.9-42.1 39.8-59.8l0 0 0 0 0 0c4.7-6.4 9-12.4 12.7-17.7zM192 128c-26.5 0-48 21.5-48 48c0 8.8-7.2 16-16 16s-16-7.2-16-16c0-44.2 35.8-80 80-80c8.8 0 16 7.2 16 16s-7.2 16-16 16zm0 384c-44.2 0-80-35.8-80-80V416H272v16c0 44.2-35.8 80-80 80z" />
  </svg>
);

export const BulbIconBlue = () => (
  <svg fill="rgb(0, 121, 211)" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
    <path d="M297.2 248.9C311.6 228.3 320 203.2 320 176c0-70.7-57.3-128-128-128S64 105.3 64 176c0 27.2 8.4 52.3 22.8 72.9c3.7 5.3 8.1 11.3 12.8 17.7l0 0c12.9 17.7 28.3 38.9 39.8 59.8c10.4 19 15.7 38.8 18.3 57.5H109c-2.2-12-5.9-23.7-11.8-34.5c-9.9-18-22.2-34.9-34.5-51.8l0 0 0 0c-5.2-7.1-10.4-14.2-15.4-21.4C27.6 247.9 16 213.3 16 176C16 78.8 94.8 0 192 0s176 78.8 176 176c0 37.3-11.6 71.9-31.4 100.3c-5 7.2-10.2 14.3-15.4 21.4l0 0 0 0c-12.3 16.8-24.6 33.7-34.5 51.8c-5.9 10.8-9.6 22.5-11.8 34.5H226.4c2.6-18.7 7.9-38.6 18.3-57.5c11.5-20.9 26.9-42.1 39.8-59.8l0 0 0 0 0 0c4.7-6.4 9-12.4 12.7-17.7zM192 128c-26.5 0-48 21.5-48 48c0 8.8-7.2 16-16 16s-16-7.2-16-16c0-44.2 35.8-80 80-80c8.8 0 16 7.2 16 16s-7.2 16-16 16zm0 384c-44.2 0-80-35.8-80-80V416H272v16c0 44.2-35.8 80-80 80z" />
  </svg>
);

export const BulbFilledIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
    <path d="M272 384c9.6-31.9 29.5-59.1 49.2-86.2l0 0c5.2-7.1 10.4-14.2 15.4-21.4c19.8-28.5 31.4-63 31.4-100.3C368 78.8 289.2 0 192 0S16 78.8 16 176c0 37.3 11.6 71.9 31.4 100.3c5 7.2 10.2 14.3 15.4 21.4l0 0c19.8 27.1 39.7 54.4 49.2 86.2H272zM192 512c44.2 0 80-35.8 80-80V416H112v16c0 44.2 35.8 80 80 80zM112 176c0 8.8-7.2 16-16 16s-16-7.2-16-16c0-61.9 50.1-112 112-112c8.8 0 16 7.2 16 16s-7.2 16-16 16c-44.2 0-80 35.8-80 80z" />
  </svg>
);

export const BulbFilledIconBlue = () => (
  <svg fill="rgb(0, 121, 211)" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
    <path d="M272 384c9.6-31.9 29.5-59.1 49.2-86.2l0 0c5.2-7.1 10.4-14.2 15.4-21.4c19.8-28.5 31.4-63 31.4-100.3C368 78.8 289.2 0 192 0S16 78.8 16 176c0 37.3 11.6 71.9 31.4 100.3c5 7.2 10.2 14.3 15.4 21.4l0 0c19.8 27.1 39.7 54.4 49.2 86.2H272zM192 512c44.2 0 80-35.8 80-80V416H112v16c0 44.2 35.8 80 80 80zM112 176c0 8.8-7.2 16-16 16s-16-7.2-16-16c0-61.9 50.1-112 112-112c8.8 0 16 7.2 16 16s-7.2 16-16 16c-44.2 0-80 35.8-80 80z" />
  </svg>
);

/* https://www.svgrepo.com/svg/326818/sparkles-outline */
export const SparklesIcon = () => (
  <svg viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg">
    <g strokeWidth="0"></g>
    <g strokeLinecap="round" strokeLinejoin="round"></g>
    <g>
      <path
        d="M259.92,262.91,216.4,149.77a9,9,0,0,0-16.8,0L156.08,262.91a9,9,0,0,1-5.17,5.17L37.77,311.6a9,9,0,0,0,0,16.8l113.14,43.52a9,9,0,0,1,5.17,5.17L199.6,490.23a9,9,0,0,0,16.8,0l43.52-113.14a9,9,0,0,1,5.17-5.17L378.23,328.4a9,9,0,0,0,0-16.8L265.09,268.08A9,9,0,0,1,259.92,262.91Z"
        fill="none"
        stroke="#000000"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="10.751999999999999"
      ></path>
      <polygon
        points="108 68 88 16 68 68 16 88 68 108 88 160 108 108 160 88 108 68"
        fill="none"
        stroke="#000000"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="10.751999999999999"
      ></polygon>
      <polygon
        points="426.67 117.33 400 48 373.33 117.33 304 144 373.33 170.67 400 240 426.67 170.67 496 144 426.67 117.33"
        fill="none"
        stroke="#000000"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="10.751999999999999"
      ></polygon>
    </g>
  </svg>
);

/* https://www.svgrepo.com/svg/326818/sparkles-outline */
export const SparklesIconBlue = () => (
  <svg viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg">
    <g strokeWidth="0"></g>
    <g strokeLinecap="round" strokeLinejoin="round"></g>
    <g>
      <path
        d="M259.92,262.91,216.4,149.77a9,9,0,0,0-16.8,0L156.08,262.91a9,9,0,0,1-5.17,5.17L37.77,311.6a9,9,0,0,0,0,16.8l113.14,43.52a9,9,0,0,1,5.17,5.17L199.6,490.23a9,9,0,0,0,16.8,0l43.52-113.14a9,9,0,0,1,5.17-5.17L378.23,328.4a9,9,0,0,0,0-16.8L265.09,268.08A9,9,0,0,1,259.92,262.91Z"
        fill="rgb(0, 121, 211)"
        stroke="rgb(0, 121, 211)"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="10.751999999999999"
      ></path>
      <polygon
        points="108 68 88 16 68 68 16 88 68 108 88 160 108 108 160 88 108 68"
        fill="rgb(0, 121, 211)"
        stroke="rgb(0, 121, 211)"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="10.751999999999999"
      ></polygon>
      <polygon
        points="426.67 117.33 400 48 373.33 117.33 304 144 373.33 170.67 400 240 426.67 170.67 496 144 426.67 117.33"
        fill="rgb(0, 121, 211)"
        stroke="rgb(0, 121, 211)"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="10.751999999999999"
      ></polygon>
    </g>
  </svg>
);
