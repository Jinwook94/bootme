import { useEffect, useState } from 'react';
import { useCourseFilters } from './useFilters';
import { COURSE_FILTERS } from '../constants/filters';

export const useCheckbox = (filterOption: string, isReset: boolean) => {
  const [isChecked, setIsChecked] = useState(false);
  const { addFilter, removeFilter, isFilterSelected, getFilterName } = useCourseFilters();
  let filterName = getFilterName(filterOption);

  const handleClick = () => {
    setIsChecked(!isChecked);

    let transformedFilterOption = filterOption;
    if (filterName === COURSE_FILTERS.TEST.filterName) {
      if (filterOption === '있음') transformedFilterOption = 'true';
      if (filterOption === '없음') transformedFilterOption = 'false';
    }

    if (filterName === COURSE_FILTERS.COST_TYPE.filterName) {
      if (filterOption === '무료') {
        transformedFilterOption = 'true';
      } else if (filterOption === '무료(국비)') {
        filterName = 'isKdt';
        transformedFilterOption = 'true';
      }
    }

    isChecked ? removeFilter(filterName, transformedFilterOption) : addFilter(filterName, transformedFilterOption);
    return true;
  };

  const handleChecked = () => {
    if (isFilterSelected(filterOption)) {
      setIsChecked(true);
    }
  };

  useEffect(() => {
    if (isReset) {
      setIsChecked(false);
    }
  }, [isReset]);

  return { isChecked, handleClick, handleChecked };
};
