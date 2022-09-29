import {createGlobalStyle} from 'styled-components';

const GlobalStyle = createGlobalStyle`
    @font-face {
      font-family: 'BMJUA';
      src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff') format('woff');
      font-weight: normal;
      font-style: normal;
      font-display: swap;
  }
    * {  
      margin: 0;
      box-sizing: border-box;
      font-family: 'BMJUA';
    } 
    a {
      text-decoration: none;
    } 
    body {
      max-width: 100%;
      overflow-x: hidden;
      background-color: #f5f5f5;
    }
    
    button {
      border: none;
      cursor: pointer;
  }
`;

export default GlobalStyle;