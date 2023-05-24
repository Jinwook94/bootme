import React, { createContext, useContext, useEffect, useState } from 'react';
import { COURSE_FILTERS } from '../constants/courseFilter';

const FilterContext = createContext<FilterContextProps>({
  selectedFilters: {},
  addFilter: () => {},
  removeFilter: () => {},
  clearAndAddFilter: () => {},
  clearFilterGroup: () => {},
  isReset: false,
  resetFilters: () => {},
  isFilterSelected: () => false,
  getFilterName: () => '',
  isModal: false,
  handleModal: () => {},
});

export const FilterProvider = ({ children }: FilterProviderProps) => {
  const [selectedFilters, setSelectedFilters] = useState<FiltersState>({
    superCategory: [],
    subCategory: [],
    languages: [],
    frameworks: [],
    costType: [],
    costInput: [],
    period: [],
    periodInput: [],
    test: [],
  });

  const addFilter = (filterName: string, filterOption: string) => {
    setSelectedFilters(prevState => {
      if (!prevState[filterName]?.includes(filterOption)) {
        return {
          ...prevState,
          [filterName]: [...(prevState[filterName] || []), filterOption],
        };
      }
      return prevState;
    });
  };

  const removeFilter = (filterName: string, filterOption: string) => {
    setSelectedFilters(prevState => ({
      ...prevState,
      [filterName]: prevState[filterName]?.filter(option => option !== filterOption),
    }));
  };

  const clearAndAddFilter = (filterName: string, filterOption: string) => {
    setSelectedFilters(prevState => ({
      ...prevState,
      [filterName]: [filterOption],
    }));
  };

  const clearFilterGroup = (filterName: string) => {
    setSelectedFilters(prevState => ({
      ...prevState,
      [filterName]: [],
    }));
  };

  const [isReset, setIsReset] = useState<boolean>(false);
  const resetFilters = () => {
    setSelectedFilters({});
    setIsReset(true);
  };

  const isFilterSelected = (filterOption: string): boolean => {
    const filterKeys = Object.keys(selectedFilters);
    return filterKeys.some(key => selectedFilters[key]?.includes(filterOption));
  };

  const getFilterName = (filterOption: string): string => {
    const filterKeys = Object.keys(COURSE_FILTERS);
    for (const key of filterKeys) {
      if (COURSE_FILTERS[key].filterOptions.includes(filterOption)) {
        return COURSE_FILTERS[key].filterName;
      }
    }
    return '';
  };

  useEffect(() => {
    if (isReset) {
      setIsReset(false);
    }
  }, [isReset]);

  const [isModal, setIsModal] = useState(false);
  const handleModal = () => {
    isModal ? setIsModal(false) : setIsModal(true);
  };

  return (
    <FilterContext.Provider
      value={{
        selectedFilters,
        addFilter,
        removeFilter,
        clearAndAddFilter,
        clearFilterGroup,
        isReset,
        resetFilters,
        isFilterSelected,
        getFilterName,
        isModal,
        handleModal,
      }}
    >
      {children}
    </FilterContext.Provider>
  );
};

export const useFilters = () => useContext(FilterContext);

interface FilterContextProps {
  selectedFilters: FiltersState;
  addFilter: (filterName: string, filterOption: string) => void;
  removeFilter: (filterName: string, filterOption: string) => void;
  clearAndAddFilter: (filterName: string, filterOption: string) => void;
  clearFilterGroup: (filterName: string) => void;
  isReset: boolean;
  resetFilters: () => void;
  isFilterSelected: (filterOption: string) => boolean;
  getFilterName: (filterOption: string) => string;
  isModal: boolean;
  handleModal: () => void;
}

interface FilterProviderProps {
  children: React.ReactNode;
}

export interface FiltersState {
  [key: string]: string[] | undefined;
  superCategory?: string[];
  subCategory?: string[];
  languages?: string[];
  frameworks?: string[];
  costType?: string[];
  costInput?: string[];
  period?: string[];
  periodInput?: string[];
  test?: string[];
}
