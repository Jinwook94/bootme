import ReactDOM from 'react-dom/client';
import App from './App';
import { RecoilRoot } from 'recoil';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';
import { QueryClient, QueryClientProvider } from 'react-query';
import { CourseFilterProvider, PostFilterProvider } from './hooks/useFilters';
import { PostProvider } from './hooks/usePost';
import { CourseProvider } from './hooks/useCourses';
import { BookmarkProvider } from './hooks/useBookmarks';
import { LoginProvider } from './hooks/useLogin';
import { NotificationProvider } from './hooks/useNotification';
import { SnackbarProvider } from './hooks/useSnackbar';
import { SecretProvider } from './hooks/useSecret';
import { ProviderBuilder } from './utils/ProviderBuilder';
import { NavigationProvider } from './hooks/useNavigation';

const rootElement = document.getElementById('root') as Element;

const ProviderWrappedApp = new ProviderBuilder(() => <App />)
  .wrap(QueryClientProvider, { client: new QueryClient() })
  .wrap(PostProvider)
  .wrap(PostFilterProvider)
  .wrap(CourseProvider)
  .wrap(CourseFilterProvider)
  .wrap(BookmarkProvider)
  .wrap(LoginProvider)
  .wrap(NotificationProvider)
  .wrap(SnackbarProvider)
  .wrap(SecretProvider)
  .wrap(NavigationProvider)
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
