import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';
import { QueryClient, QueryClientProvider } from 'react-query';
import { FilterProvider } from './hooks/useFilters';
import { LoginProvider } from './hooks/useLogin';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import ReactDOM from 'react-dom/client';
import { BookmarkProvider } from './hooks/useBookmarks';
import { NotificationProvider } from './hooks/useNotification';

const rootElement = document.getElementById('root') as Element;
const queryClient = new QueryClient();

ReactDOM.createRoot(rootElement).render(
  <BrowserRouter>
    <NotificationProvider>
      <LoginProvider>
        <GoogleOAuthProvider clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID as string}>
          <BookmarkProvider>
            <FilterProvider>
              <QueryClientProvider client={queryClient}>
                <ThemeProvider theme={theme}>
                  <GlobalStyle />
                  <App />
                </ThemeProvider>
              </QueryClientProvider>
            </FilterProvider>
          </BookmarkProvider>
        </GoogleOAuthProvider>
      </LoginProvider>
    </NotificationProvider>
  </BrowserRouter>
);
