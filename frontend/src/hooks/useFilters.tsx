import React, { createContext, useContext, useState } from 'react';

const FilterContext = createContext<FilterContextProps>({
  selectedFilters: [],
  addFilter: () => {},
  removeBeforeAdd: () => {},
  removeFilter: () => {},
  removeSpecificLabelFilters: () => {},
});

export const FilterProvider = ({ children }: FilterProviderProps) => {
  const [selectedFilters, setSelectedFilters] = useState<string[]>([]);

  const addFilter = (filter: string) => {
    setSelectedFilters([...selectedFilters, filter]);
  };

  const removeBeforeAdd = (label: string, currentValue: number) => {
    const newSelectedFilters = selectedFilters.filter(
      filter => filter.startsWith(label) && Number(filter.split('-')[1]) >= currentValue
    );
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

  /*
   * range, text input 필터에서 최댓값이 입력된 경우에,
   * 해당 필터를 모두 제거하여 모든 코스가 표시될 수 있도록 함
   * ex) 비용 필터에서 '1000' 이 입력됨 -> 비용 필터 모두 제거
   * */
  const removeSpecificLabelFilters = (label: string) => {
    const newSelectedFilters = selectedFilters.filter(filter => filter.startsWith(label));
    for (const filter of newSelectedFilters) {
      removeFilter(filter);
    }
  };

  return (
    <FilterContext.Provider
      value={{ selectedFilters, addFilter, removeBeforeAdd, removeFilter, removeSpecificLabelFilters }}
    >
      {children}
    </FilterContext.Provider>
  );
};

export const useFilters = () => useContext(FilterContext);

interface FilterContextProps {
  selectedFilters: string[];
  removeBeforeAdd: (label: string, currentValue: number) => void;
  addFilter: (filter: string) => void;
  removeFilter: (filter: string) => void;
  removeSpecificLabelFilters: (label: string) => void;
}

interface FilterProviderProps {
  children: React.ReactNode;
}
