import React, { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { COURSE_FILTERS, POST_FILTERS } from '../constants/filters';

const createFilterContext = (defaultFilters: FiltersState, filterOptions: typeof COURSE_FILTERS) => {
  const context = createContext<FilterContextProps>({
    selectedFilters: defaultFilters,
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

  const Provider = ({ children }: FilterProviderProps) => {
    const [selectedFilters, setSelectedFilters] = useState<FiltersState>({});

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
      const filterKeys = Object.keys(filterOptions);
      for (const key of filterKeys) {
        if (filterOptions[key].filterOptions.includes(filterOption)) {
          return filterOptions[key].filterName;
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

    const contextValue = useMemo(
      () => ({
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
      }),
      [selectedFilters, isReset, isModal]
    );

    return <context.Provider value={contextValue}>{children}</context.Provider>;
  };

  return [context, Provider] as const;
};

const COURSE_FILTERS_INITIAL_STATE = {
  search: [],
  superCategories: [],
  subCategories: [],
  languages: [],
  frameworks: [],
  isFree: [],
  isKdt: [],
  isTested: [],
  costInput: [],
  period: [],
  periodInput: [],
};

const POST_FILTERS_INITIAL_STATE = {
  search: [],
  topic: [],
  tag: [],
};

const [CourseFilterContext, CourseFilterProvider] = createFilterContext(COURSE_FILTERS_INITIAL_STATE, COURSE_FILTERS);
const [PostFilterContext, PostFilterProvider] = createFilterContext(POST_FILTERS_INITIAL_STATE, POST_FILTERS);

const useCourseFilters = () => useContext(CourseFilterContext);
const usePostFilters = () => useContext(PostFilterContext);

export { CourseFilterProvider, useCourseFilters, PostFilterProvider, usePostFilters };

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
}
