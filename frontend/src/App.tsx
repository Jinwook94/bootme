import React from 'react';
import Home from './pages/Home';
import { QueryClient, QueryClientProvider } from 'react-query';
import { FilterProvider } from './hooks/useFilters';
import { LoginProvider } from './hooks/useLogin';

const queryClient = new QueryClient();

const App = () => {
  return (
    <LoginProvider>
      <FilterProvider>
        <QueryClientProvider client={queryClient}>
          <Home />
        </QueryClientProvider>
      </FilterProvider>
    </LoginProvider>
  );
};

export default App;
