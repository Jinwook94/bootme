import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';
import { QueryClient, QueryClientProvider } from 'react-query';
import { FilterProvider } from './hooks/useFilters';
import { LoginProvider } from './hooks/useLogin';
import { GoogleOAuthProvider } from '@react-oauth/google';
import 'bootstrap/dist/css/bootstrap.min.css';

import App from './App';
import ReactDOM from 'react-dom/client';

const rootElement = document.getElementById('root') as Element;
const queryClient = new QueryClient();

ReactDOM.createRoot(rootElement).render(
  <GoogleOAuthProvider clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID as string}>
    <LoginProvider>
      <FilterProvider>
        <QueryClientProvider client={queryClient}>
          <ThemeProvider theme={theme}>
            <GlobalStyle />
            <App />
          </ThemeProvider>
        </QueryClientProvider>
      </FilterProvider>
    </LoginProvider>
  </GoogleOAuthProvider>
);
