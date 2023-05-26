import { useFilters } from './useFilters';

export const useSearch = () => {
  const { clearAndAddFilter } = useFilters();
  const onSearch = (value: string) => {
    clearAndAddFilter('search', value);
  };

  return { onSearch };
};
