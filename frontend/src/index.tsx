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
import { SecretProvider } from './hooks/useSecret';

const rootElement = document.getElementById('root') as Element;
const queryClient = new QueryClient();

ReactDOM.createRoot(rootElement).render(
  <BrowserRouter>
    <SecretProvider>
      <NotificationProvider>
        <LoginProvider>
          <GoogleOAuthProvider clientId={'GOOGLE_CLIENT_ID'}>
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
    </SecretProvider>
  </BrowserRouter>
);
