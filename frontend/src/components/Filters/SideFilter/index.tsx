import SideFilterItem from '../SideFilterItem';
import {
  SearchInput,
  StyledSearch,
  Wrapper,
  FilterReset,
  ResetButton,
  FilterItemWrapper,
  ResetIconWrapper,
} from './style';
import { COURSE_FILTERS } from '../../../constants/filters';
import { useCourseFilters } from '../../../hooks/useFilters';
import { ResetIcon } from '../../../constants/icons';
import { Space } from 'antd';
import { useCourses } from '../../../hooks/useCourses';

const SideFilter = () => {
  const { isReset, resetFilters } = useCourseFilters();
  const { onSearch } = useCourses();
  return (
    <Wrapper>
      <SearchInput>
        <Space direction="vertical">
          <StyledSearch placeholder="커리큘럼 검색" onSearch={onSearch} size="large" style={{ width: 266 }} />
        </Space>
      </SearchInput>
      <FilterReset>
        <ResetButton style={{ cursor: 'default' }}>
          <ResetIconWrapper onClick={resetFilters}>
            <ResetIcon />
            <span>필터 초기화</span>
          </ResetIconWrapper>
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
