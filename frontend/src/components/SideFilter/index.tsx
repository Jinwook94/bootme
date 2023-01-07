import FilterItem from './FilterItem';
import { FilterItemWrapper, FilterReset, ResetButton, Wrapper } from './style';
import { COURSE_FILTER } from '../../constants/courseFilter';
import { useFilters } from '../../hooks/useFilters';
import { ResetIcon } from '../../constants/icons';

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
        {Object.values(COURSE_FILTER).map(filterGroup => (
          <FilterItem
            key={filterGroup.filterName}
            filterName={filterGroup.filterName}
            filterOptions={filterGroup.filterOptions}
            isMore={filterGroup.isMore}
            isReset={isReset}
            borderTop={filterGroup.borderTop}
          />
        ))}
      </FilterItemWrapper>
    </Wrapper>
  );
};

export default SideFilter;
