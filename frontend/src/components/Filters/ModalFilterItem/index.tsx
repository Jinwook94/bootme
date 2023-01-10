import FilterOption from '../FilterOption';
import { CourseFilterTypes } from '../../../constants/courseFilter';
import React, { useRef, useState } from 'react';
import { Wrapper, Title, MoreButton, MoreOptions } from './style';

const ModalFilterItem = ({ filterName, filterOptions, isMore, isReset }: ModalFilterItemProps) => {
  const [isMoreOpen, setIsMoreOpen] = useState(false);
  const wrapperRef = useRef<HTMLDivElement>(null);

  const handleMoreClick = () => {
    setIsMoreOpen(!isMoreOpen);
  };

  const handleHideClick = () => {
    if (wrapperRef.current) {
      wrapperRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <Wrapper ref={wrapperRef}>
      <Title>{filterName}</Title>
      {filterOptions.slice(0, 5).map((filterOption: string, index) => (
        <FilterOption
          key={index}
          filterType={'MODAL_FILTER'}
          filterName={filterName}
          filterOption={filterOption}
          isReset={isReset}
        />
      ))}
      {isMore && !isMoreOpen ? (
        <MoreButton onClick={handleMoreClick}>
          <span>더 표시</span>
        </MoreButton>
      ) : null}
      <MoreOptions isMoreOpen={isMoreOpen}>
        {isMoreOpen &&
          filterOptions
            .slice(5)
            .map((filterOption: string, index) => (
              <FilterOption
                key={index}
                filterType={'MODAL_FILTER'}
                filterName={filterName}
                filterOption={filterOption}
                isReset={isReset}
              />
            ))}
        {isMore && isMoreOpen ? (
          <MoreButton onClick={handleMoreClick}>
            <span onClick={handleHideClick}>숨기기</span>
          </MoreButton>
        ) : null}
      </MoreOptions>
    </Wrapper>
  );
};

export type ModalFilterItemProps = CourseFilterTypes & {
  filter: CourseFilterTypes;
  isReset: boolean;
};

export default ModalFilterItem;
