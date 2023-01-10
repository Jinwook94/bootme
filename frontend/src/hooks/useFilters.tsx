import React, { createContext, useContext, useEffect, useState } from 'react';
import { CATEGORIES, COST_INPUT, COST_TYPE, PERIOD, STACKS, TEST } from '../constants/courseFilter';

const FilterContext = createContext<FilterContextProps>({
  selectedFilters: [],
  addFilter: () => {},
  removeBeforeAdd: () => {},
  removeFilter: () => {},
  filterCourses: () => [],
  resetFilters: () => {},
  isReset: false,
  isModal: false,
  handleModal: () => {},
});

export const FilterProvider = ({ children }: FilterProviderProps) => {
  const [selectedFilters, setSelectedFilters] = useState<string[]>([]);
  const [isReset, setIsReset] = useState<boolean>(false);

  /**
   * 필터링 함수 (필터 추가, 제거 ...)
   * */

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
          case CATEGORIES.filterName:
            categories.push(value);
            break;
          case STACKS.filterName:
            stacks.push(value);
            break;
          case COST_TYPE.filterName:
            costTypes.push(value);
            break;
          case COST_INPUT.filterName:
            costs.push(value);
            break;
          case PERIOD.filterName:
            periods.push(value);
            break;
          case TEST.filterName:
            tested.push(value);
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

  /**
   * 필터 초기화
   * */

  const resetFilters = () => {
    setSelectedFilters([]);
    setIsReset(true);
  };

  useEffect(() => {
    if (isReset) {
      setIsReset(false);
    }
  }, [isReset]);

  /**
   * 모달 필터 (모바일 스크린에서 사용됨)
   * */

  const [isModal, setIsModal] = useState(false);
  const handleModal = () => {
    isModal ? setIsModal(false) : setIsModal(true);
  };

  return (
    <FilterContext.Provider
      value={{
        selectedFilters,
        addFilter,
        removeBeforeAdd,
        removeFilter,
        filterCourses,
        isReset,
        resetFilters,
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
  selectedFilters: string[];
  removeBeforeAdd: (label: string) => void;
  addFilter: (filterName: string, filterOption: string) => void;
  removeFilter: (filterName: string, filterOption: string) => void;
  filterCourses: (courses: Course[]) => Course[];
  isReset: boolean;
  resetFilters: () => void;
  isModal: boolean;
  handleModal: () => void;
}

interface FilterProviderProps {
  children: React.ReactNode;
}
