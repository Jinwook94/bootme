import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';
import { QueryClient, QueryClientProvider } from 'react-query';
import { CourseFilterProvider, PostFilterProvider } from './hooks/useFilters';
import { LoginProvider } from './hooks/useLogin';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import ReactDOM from 'react-dom/client';
import { BookmarkProvider } from './hooks/useBookmarks';
import { NotificationProvider } from './hooks/useNotification';
import { SecretProvider } from './hooks/useSecret';
import { CourseProvider } from './hooks/useCourses';
import { RecoilRoot } from 'recoil';
import { SnackbarProvider } from './hooks/useSnackbar';

const rootElement = document.getElementById('root') as Element;

const ProviderWrappedApp = new ProviderBuilder(() => <App />)
  .wrap(QueryClientProvider, { client: new QueryClient() })
  .wrap(PostFilterProvider)
  .wrap(CourseProvider)
  .wrap(CourseFilterProvider)
  .wrap(BookmarkProvider)
  .wrap(GoogleOAuthProvider, { clientId: 'GOOGLE_CLIENT_ID' })
  .wrap(LoginProvider)
  .wrap(NotificationProvider)
  .wrap(SnackbarProvider)
  .wrap(SecretProvider)
  .build();

ReactDOM.createRoot(rootElement).render(
  <RecoilRoot>
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <ProviderWrappedApp />
      </ThemeProvider>
    </BrowserRouter>
  </RecoilRoot>
);
