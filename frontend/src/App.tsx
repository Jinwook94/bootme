import React from 'react';
import Home from './pages/Home';
import { QueryClient, QueryClientProvider } from 'react-query';
import { FilterProvider } from './hooks/useFilters';
import { LoginProvider } from './hooks/useLogin';
import { GoogleOAuthProvider } from '@react-oauth/google';

const queryClient = new QueryClient();

const App = () => {
  return (
    <GoogleOAuthProvider clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID as string}>
      <LoginProvider>
        <FilterProvider>
          <QueryClientProvider client={queryClient}>
            <Home />
          </QueryClientProvider>
        </FilterProvider>
      </LoginProvider>
    </GoogleOAuthProvider>
  );
};

export default App;
