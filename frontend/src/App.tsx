import React from 'react';
import Home from './pages/Home';
import { QueryClient, QueryClientProvider } from 'react-query';
import { FilterProvider } from './hooks/useFilters';

const queryClient = new QueryClient();

const App = () => {
  return (
    <FilterProvider>
      <QueryClientProvider client={queryClient}>
        <Home />
      </QueryClientProvider>
    </FilterProvider>
  );
};

export default App;
