import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';

import App from './App';
import ReactDOM from 'react-dom/client';

const rootElement = document.getElementById('root') as Element;

ReactDOM.createRoot(rootElement).render(
  <ThemeProvider theme={theme}>
    <GlobalStyle />
    <App />
  </ThemeProvider>
);
