import { useEffect, useState } from 'react';
import { useFilters } from './useFilters';

export const useCheckbox = (filterName: string, filterOption: string, isReset: boolean) => {
  const [isChecked, setIsChecked] = useState(false);
  const { addFilter, removeFilter } = useFilters();

  const handleClick = () => {
    setIsChecked(!isChecked);
    isChecked ? removeFilter(filterName, filterOption) : addFilter(filterName, filterOption);
    return true;
  };

  const resetFilter = () => {
    useEffect(() => {
      if (isReset) {
        setIsChecked(false);
      }
    }, [isReset]);
  };

  return { isChecked, handleClick, resetFilter };
};
