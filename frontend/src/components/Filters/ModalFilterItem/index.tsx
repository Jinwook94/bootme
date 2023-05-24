import FilterOption from '../FilterOption';
import { COURSE_FILTERS, CourseFilterTypes, MODAL_FILTER, PERIOD_INPUT } from '../../../constants/courseFilter';
import React, { useRef, useState } from 'react';
import { Wrapper, Title, MoreButton, MoreOptions } from './style';
import RangeBar from '../RangeBar';

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

  switch (filterName) {
    case '개발 분야':
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          {filterOptions.slice(0, 3).map((filterOption: string, index) => (
            <FilterOption key={index} filterType={MODAL_FILTER} filterOption={filterOption} isReset={isReset} />
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
    case '기술 스택':
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          {filterOptions.slice(0, 3).map((filterOption: string, index) => (
            <FilterOption
              key={index}
              filterType={MODAL_FILTER}
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
                  <FilterOption key={index} filterType={MODAL_FILTER} filterOption={filterOption} isReset={isReset} />
                ))}
            {isMore && isMoreOpen ? (
              <MoreButton onClick={handleMoreClick}>
                <span onClick={handleHideClick}>숨기기</span>
              </MoreButton>
            ) : null}
          </MoreOptions>
        </Wrapper>
      );
    case '수강 비용':
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          <FilterOption filterType={MODAL_FILTER} filterOption={filterOptions[0]} isReset={isReset} />
          <FilterOption filterType={MODAL_FILTER} filterOption={filterOptions[1]} isReset={isReset} />
          <RangeBar filterType={MODAL_FILTER} filterName={COURSE_FILTERS.COST_TYPE.filterName} isReset={isReset} />
        </Wrapper>
      );
    case '수강 기간':
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          <RangeBar filterType={MODAL_FILTER} filterName={PERIOD_INPUT.filterName} isReset={isReset} />
        </Wrapper>
      );
    case '코딩 테스트':
      return (
        <Wrapper ref={wrapperRef}>
          <Title>{filterName}</Title>
          <FilterOption filterType={MODAL_FILTER} filterOption={filterOptions[0]} isReset={isReset} />
          <FilterOption filterType={MODAL_FILTER} filterOption={filterOptions[1]} isReset={isReset} />
        </Wrapper>
      );
    default:
      return null;
  }
};

export type ModalFilterItemProps = CourseFilterTypes & {
  isReset: boolean;
};

export default ModalFilterItem;
