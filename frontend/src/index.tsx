import React, { useEffect, useState } from 'react';
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
import { GoogleOAuthProvider } from '@react-oauth/google';
import { LoginProvider } from './hooks/useLogin';
import { NotificationProvider } from './hooks/useNotification';
import { SnackbarProvider } from './hooks/useSnackbar';
import { SecretProvider, useSecret } from './hooks/useSecret';
import { ProviderBuilder } from './utils/ProviderBuilder';
import { ProfileProvider } from './hooks/useProfile';
import { StackProvider } from './hooks/useStacks';
import { ModalsProvider } from '@mantine/modals';
import { MantineProvider } from '@mantine/core';

const rootElement = document.getElementById('root') as Element;

const BootstrapApp = () => {
  const { secrets, fetchSecrets } = useSecret();
  const [isSecretsFetched, setSecretsFetched] = useState(false);

  useEffect(() => {
    const loadSecrets = async () => {
      await fetchSecrets();
      setSecretsFetched(true);
    };
    loadSecrets();
  }, [fetchSecrets]);

  if (!isSecretsFetched) {
    return null;
  }

  const ProviderWrappedApp = new ProviderBuilder(() => <App />)
    .wrap(QueryClientProvider, { client: new QueryClient() })
    .wrap(ModalsProvider)
    .wrap(MantineProvider)
    .wrap(ProfileProvider)
    .wrap(StackProvider)
    .wrap(PostProvider)
    .wrap(PostFilterProvider)
    .wrap(CourseProvider)
    .wrap(CourseFilterProvider)
    .wrap(BookmarkProvider)
    .wrap(GoogleOAuthProvider, { clientId: secrets?.['googleClientId'] as string })
    .wrap(NotificationProvider)
    .wrap(LoginProvider)
    .wrap(SnackbarProvider)
    .build();

  return (
    <RecoilRoot>
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <GlobalStyle />
          <ProviderWrappedApp />
        </ThemeProvider>
      </BrowserRouter>
    </RecoilRoot>
  );
};

ReactDOM.createRoot(rootElement).render(
  <SecretProvider>
    <BootstrapApp />
  </SecretProvider>
);
