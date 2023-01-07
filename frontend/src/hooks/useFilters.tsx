import React, { createContext, useContext, useEffect, useState } from 'react';

const FilterContext = createContext<FilterContextProps>({
  selectedFilters: [],
  addFilter: () => {},
  removeBeforeAdd: () => {},
  removeFilter: () => {},
  filterCourses: () => [],
  resetFilters: () => {},
  isReset: false,
});

export const FilterProvider = ({ children }: FilterProviderProps) => {
  const [selectedFilters, setSelectedFilters] = useState<string[]>([]);
  const [isReset, setIsReset] = useState<boolean>(false);

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

  const filterCourses = (courses: Course[]) => {
    return courses.filter((course: Course) => {
      const categories: string[] = [];
      const stacks: string[] = [];
      const costTypes: string[] = [];
      const tested: string[] = [];
      const costs: string[] = [];
      const periods: string[] = [];

      selectedFilters.forEach(filter => {
        const [property, value] = filter.split(':');
        switch (property) {
          case '개발 분야':
            categories.push(value);
            break;
          case '기술 스택':
            stacks.push(value);
            break;
          case '수강 비용':
            costTypes.push(value);
            break;
          case '코딩 테스트':
            tested.push(value);
            break;
          case '비용':
            costs.push(value);
            break;
          case '수강 기간':
            periods.push(value);
            break;
        }
      });

      return (
        (!categories.length ||
          categories.some(
            category => course.categories.super.includes(category) || course.categories.sub.includes(category)
          )) &&
        (!stacks.length ||
          stacks.some(stack => course.stacks.languages.includes(stack) || course.stacks.frameworks.includes(stack))) &&
        (!costTypes.length || costTypes.some(costType => course.costType === costType)) &&
        (!costs.length || costs.some(cost => course.cost <= parseInt(cost))) &&
        (!periods.length || periods.some(period => Math.floor(course.period / 30) + 1 <= parseInt(period))) &&
        (!tested.length || tested.some(test => (String(course.tested) === 'true' ? '있음' : '없음') === test))
      );
    });
  };

  const resetFilters = () => {
    setSelectedFilters([]);
    setIsReset(true);
  };

  useEffect(() => {
    if (isReset) {
      setIsReset(false);
    }
  }, [isReset]);

  return (
    <FilterContext.Provider
      value={{ selectedFilters, addFilter, removeBeforeAdd, removeFilter, filterCourses, isReset, resetFilters }}
    >
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
  filterCourses: (courses: Course[]) => Course[];
  isReset: boolean;
  resetFilters: () => void;
}

interface FilterProviderProps {
  children: React.ReactNode;
}
