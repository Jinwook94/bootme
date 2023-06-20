import {
  BoxWrapper,
  CheckBoxWrapper1,
  CheckBoxWrapper2,
  CheckedWrapper1,
  CheckedWrapper2,
  ListItem,
  ModalFilterCheckbox,
  ModalWrapper,
  Name,
  Option,
  Unchecked,
  Wrapper,
} from './style';
import React, { useEffect } from 'react';
import { useCheckbox } from '../../../hooks/useCheckbox';
import { MODAL_FILTER, SIDE_FILTER } from '../../../constants/filters';
import { CheckIcon } from '../../../constants/icons';
import { useCourseFilters } from '../../../hooks/useFilters';
import { Checkbox } from '@mantine/core';

export const FilterOption = ({ filterType, filterOption, isReset, borderTop }: FilterOptionProps) => {
  const { isChecked, handleClick, handleChecked } = useCheckbox(filterOption, isReset);
  const { isModal } = useCourseFilters();
  const styles = {
    checkbox: { input: { cursor: 'pointer' }, label: { cursor: 'pointer' } },
  };

  useEffect(() => {
    if (filterType === MODAL_FILTER) {
      handleChecked();
    }
  }, [isModal]);

  switch (filterType) {
    case SIDE_FILTER:
      return (
        <ListItem onClick={handleClick} borderTop={borderTop}>
          <Wrapper>
            <BoxWrapper>
              <Checkbox size="xs" mr={6} checked={isChecked} styles={styles.checkbox} />
            </BoxWrapper>
            <Option>{filterOption}</Option>
          </Wrapper>
        </ListItem>
      );
    case MODAL_FILTER:
      return (
        <ModalWrapper onClick={handleClick}>
          <Name>{filterOption}</Name>
          <CheckBoxWrapper1>
            <CheckBoxWrapper2>
              <ModalFilterCheckbox
                checked={isChecked}
                onChange={handleClick}
                style={{ appearance: isChecked ? 'auto' : 'none' }}
              />
              {isChecked ? (
                <CheckedWrapper1>
                  <CheckedWrapper2>
                    <CheckIcon />
                  </CheckedWrapper2>
                </CheckedWrapper1>
              ) : (
                <Unchecked />
              )}
            </CheckBoxWrapper2>
          </CheckBoxWrapper1>
        </ModalWrapper>
      );
    default:
      return (
        <ListItem onClick={handleClick} borderTop={borderTop}>
          <Wrapper>
            <BoxWrapper>
              <Checkbox size="xs" mr={6} checked={isChecked} />
            </BoxWrapper>
            <Option>{filterOption}</Option>
          </Wrapper>
        </ListItem>
      );
  }
};

export interface FilterOptionProps {
  filterType: string;
  filterOption: string;
  isReset: boolean;
  borderTop?: boolean | undefined;
}

export default FilterOption;
