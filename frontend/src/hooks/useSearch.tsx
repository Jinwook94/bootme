import { useCourseFilters } from './useFilters';

export const useSearch = () => {
  const { clearAndAddFilter } = useCourseFilters();
  const onSearch = (value: string) => {
    clearAndAddFilter('search', value);
  };

  return { onSearch };
};
