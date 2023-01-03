import React, { createContext, useContext, useState } from 'react';

const FilterContext = createContext<FilterContextProps>({
  selectedFilters: [],
  addFilter: () => {},
  removeBeforeAdd: () => {},
  removeFilter: () => {},
});

export const FilterProvider = ({ children }: FilterProviderProps) => {
  const [selectedFilters, setSelectedFilters] = useState<string[]>([]);

  const addFilter = (filter: string) => {
    setSelectedFilters([...selectedFilters, filter]);
  };

  const removeBeforeAdd = (label: string) => {
    const newSelectedFilters = selectedFilters.filter(filter => filter.startsWith(label));
    for (const filter of newSelectedFilters) {
      removeFilter(filter);
    }
  };

  const removeFilter = (filter: string) => {
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
  addFilter: (filter: string) => void;
  removeFilter: (filter: string) => void;
}

interface FilterProviderProps {
  children: React.ReactNode;
}
