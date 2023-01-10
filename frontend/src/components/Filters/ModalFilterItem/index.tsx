import FilterOption from '../FilterOption';
import {
  CATEGORIES,
  COST_TYPE,
  CourseFilterTypes,
  MODAL_FILTER,
  PERIOD,
  STACKS,
  TEST,
} from '../../../constants/courseFilter';
import React, { useRef, useState } from 'react';
import { Wrapper, Title, MoreButton, MoreOptions } from './style';
import RangeBar from '../RangeBar';

const ModalFilterItem = ({ filter, filterName, filterOptions, isMore, isReset }: ModalFilterItemProps) => {
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

  switch (filter) {
    case CATEGORIES:
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          {filterOptions.slice(0, 3).map((filterOption: string, index) => (
            <FilterOption
              key={index}
              filterType={MODAL_FILTER}
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
                .slice(3)
                .map((filterOption: string, index) => (
                  <FilterOption
                    key={index}
                    filterType={MODAL_FILTER}
                    filterName={filterName}
                    filterOption={filterOption}
                    isReset={isReset}
                    borderTop={index === 9}
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
    case STACKS:
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          {filterOptions.slice(0, 3).map((filterOption: string, index) => (
            <FilterOption
              key={index}
              filterType={MODAL_FILTER}
              filterName={filterName}
              filterOption={filterOption}
              isReset={isReset}
              borderTop={index === 8}
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
                .slice(3)
                .map((filterOption: string, index) => (
                  <FilterOption
                    key={index}
                    filterType={MODAL_FILTER}
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
    case COST_TYPE:
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          <FilterOption
            filterType={MODAL_FILTER}
            filterName={filterName}
            filterOption={filterOptions[0]}
            isReset={isReset}
          />
          <FilterOption
            filterType={MODAL_FILTER}
            filterName={filterName}
            filterOption={filterOptions[1]}
            isReset={isReset}
          />
          <RangeBar filterName={filterName} isReset={isReset} />
        </Wrapper>
      );
    case PERIOD:
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          <RangeBar filterName={filterName} isReset={isReset} />
        </Wrapper>
      );
    case TEST:
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          <FilterOption
            filterType={MODAL_FILTER}
            filterName={filterName}
            filterOption={filterOptions[0]}
            isReset={isReset}
          />
          <FilterOption
            filterType={MODAL_FILTER}
            filterName={filterName}
            filterOption={filterOptions[1]}
            isReset={isReset}
          />
        </Wrapper>
      );
    default:
      return null;
  }
};

export type ModalFilterItemProps = CourseFilterTypes & {
  filter: CourseFilterTypes;
  isReset: boolean;
};

export default ModalFilterItem;
