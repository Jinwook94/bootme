import React, { createContext, useContext, useState } from 'react';

const FilterContext = createContext<FilterContextProps>({
  selectedFilters: [],
  addFilter: () => {},
  removeBeforeAdd: () => {},
  removeFilter: () => {},
});

export const FilterProvider = ({ children }: FilterProviderProps) => {
  const [selectedFilters, setSelectedFilters] = useState<string[]>([]);

  const addFilter = (filterName: string, filterOption: string) => {
    const filter = filterName + ':' + filterOption;
    setSelectedFilters([...selectedFilters, filter]);
  };

  const removeBeforeAdd = (filterName: string) => {
    const newSelectedFilters = selectedFilters.filter(filter => filter.startsWith(filterName));
    for (const filter of newSelectedFilters) {
      const index = selectedFilters.indexOf(filter);
      if (index !== -1) {
        selectedFilters.splice(index, 1);
      }
      setSelectedFilters(selectedFilters.filter(f => f !== filter));
    }
  };

  const removeFilter = (filterName: string, filterOption: string) => {
    const filter = `${filterName}:${filterOption}`;
    const index = selectedFilters.indexOf(filter);
    if (index !== -1) {
      selectedFilters.splice(index, 1);
    }
    setSelectedFilters(selectedFilters.filter(f => f !== filter));
  };

  return (
    <FilterContext.Provider value={{ selectedFilters, addFilter, removeBeforeAdd, removeFilter }}>
      {children}
    </FilterContext.Provider>
  );
};

export const useFilters = () => useContext(FilterContext);

interface FilterContextProps {
  selectedFilters: string[];
  removeBeforeAdd: (label: string) => void;
  addFilter: (filterName: string, filterOption: string) => void;
  removeFilter: (filterName: string, filterOption: string) => void;
}

interface FilterProviderProps {
  children: React.ReactNode;
}
