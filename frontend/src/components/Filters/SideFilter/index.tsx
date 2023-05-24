import SideFilterItem from '../SideFilterItem';
import { FilterItemWrapper, FilterReset, ResetButton, Wrapper } from './style';
import { COURSE_FILTERS } from '../../../constants/courseFilter';
import { useFilters } from '../../../hooks/useFilters';
import { ResetIcon } from '../../../constants/icons';

const SideFilter = () => {
  const { isReset, resetFilters } = useFilters();
  return (
    <Wrapper>
      <FilterReset>
        <ResetButton style={{ cursor: 'default' }}>
          <div onClick={resetFilters} style={{ cursor: 'pointer' }}>
            <ResetIcon />
            <span>필터 초기화</span>
          </div>
        </ResetButton>
      </FilterReset>
      <FilterItemWrapper>
        <SideFilterItem
          filterName={'개발 분야'}
          filterOptions={[...COURSE_FILTERS.SUPER_CATEGORY.filterOptions, ...COURSE_FILTERS.SUB_CATEGORY.filterOptions]}
          isMore={true}
          isReset={isReset}
        />
        <SideFilterItem
          filterName={'기술 스택'}
          filterOptions={[...COURSE_FILTERS.LANGUAGES.filterOptions, ...COURSE_FILTERS.FRAMEWORKS.filterOptions]}
          isMore={true}
          isReset={isReset}
        />
        <SideFilterItem
          filterName={'수강 비용'}
          filterOptions={COURSE_FILTERS.COST_TYPE.filterOptions}
          isMore={false}
          isReset={isReset}
        />
        <SideFilterItem
          filterName={'수강 기간'}
          filterOptions={COURSE_FILTERS.PERIOD.filterOptions}
          isMore={false}
          isReset={isReset}
        />
        <SideFilterItem
          filterName={'코딩 테스트'}
          filterOptions={COURSE_FILTERS.TEST.filterOptions}
          isMore={false}
          isReset={isReset}
        />
      </FilterItemWrapper>
    </Wrapper>
  );
};

export default SideFilter;
