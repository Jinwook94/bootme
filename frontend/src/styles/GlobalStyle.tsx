import {createGlobalStyle} from 'styled-components';

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
      padding:0;
      box-sizing: border-box;
      font-size: 100%;
      vertical-align: baseline;
      color: inherit;
    } 
    
    html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
      box-sizing: border-box;
      padding: 0;
      border: 0;
      margin: 0;
      font-size: 100%;
      vertical-align: baseline;
    }
    
    a {
      text-decoration: none;
    } 
    body {
      max-width: 100%;
      overflow-x: hidden;
      font-display: 'block';
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
      width: 100%;
      margin: 0;
      word-break: keep-all;
      word-wrap: break-word;
      color: #263747;
      overflow-x: hidden;
    }
    
    button {
      border: none;
      cursor: pointer;
  }
`;

export default GlobalStyle;