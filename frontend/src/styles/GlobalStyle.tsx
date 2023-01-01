import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  @font-face {
    font-family: 'Noto Sans KR', sans-serif;
    font-weight: normal;
    font-style: normal;
    font-display: swap;
  }

  * {
    font-family: 'Noto Sans KR', sans-serif;
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    vertical-align: baseline;
    color: inherit;
  }

  body {
    max-width: 100%;
    overflow-x: hidden;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    width: 100%;
    margin: 0;
    word-break: keep-all;
    word-wrap: break-word;
    color: #263747;
  }

  a {
    text-decoration: none;
    color: inherit;
  }
  
  a:hover {
    text-decoration: none;
    color: inherit;
  }
  
  ul {
    margin-bottom: 0;
  }

  h1 {
    font-size: 38px;
    line-height: 46px;
  }

  h2 {
    font-size: 30px;
    line-height: 40px;
  }

  h3 {
    font-size: 24px;
    line-height: 32px;
  }

  h4 {
    font-size: 20px;
    line-height: 28px;
  }

  h5 {
    font-size: 16px;
    line-height: 24px;
  }

  button {
    border: none;
    cursor: pointer;
  }
`;

export default GlobalStyle;
