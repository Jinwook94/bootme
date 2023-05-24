export const SIDE_FILTER = 'SIDE_FILTER';
export const MODAL_FILTER = 'MODAL_FILTER';

export const COURSE_FILTERS: { [key: string]: CourseFilterTypes } = {
  SUPER_CATEGORY: {
    filterName: 'superCategory',
    filterOptions: ['웹', '모바일 앱', 'AI', '데이터', '데브옵스', '게임'],
  },
  SUB_CATEGORY: {
    filterName: 'subCategory',
    filterOptions: ['프론트엔드', '백엔드', '풀스택', '안드로이드', 'iOS'],
  },
  LANGUAGES: {
    filterName: 'languages',
    filterOptions: ['JavaScript', 'TypeScript', 'Java', 'Python', 'Swift', 'Kotlin'],
  },
  FRAMEWORKS: {
    filterName: 'frameworks',
    filterOptions: ['React', 'Vue.js', 'Spring', 'Node.js', 'Django'],
  },
  COST_TYPE: { filterName: 'costType', filterOptions: ['무료', '무료(국비)'], isMore: false },
  PERIOD: { filterName: 'period', filterOptions: [], isMore: false },
  TEST: { filterName: 'isTested', filterOptions: ['있음', '없음'], isMore: false },
};

export const COST_INPUT = {
  filterName: 'costInput',
  minValue: 0,
  maxValue: 1000,
  step: 50,
  unit: '만원 이하',
};
export const PERIOD_INPUT = {
  filterName: 'periodInput',
  minValue: 0,
  maxValue: 12,
  step: 1,
  unit: '개월 이하',
};

export interface CourseFilterTypes {
  filterName: string;
  filterOptions: string[];
  isMore?: boolean;
}
