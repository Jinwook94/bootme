import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';

import App from './App';
import ReactDOM from 'react-dom';

const rootElement = document.getElementById('root');

ReactDOM.render(
  <ThemeProvider theme={theme}>
    <GlobalStyle />
    <App />
  </ThemeProvider>,
  rootElement
);
