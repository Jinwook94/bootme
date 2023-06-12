export const SIDE_FILTER = 'SIDE_FILTER';
export const MODAL_FILTER = 'MODAL_FILTER';

export const COURSE_FILTERS: { [key: string]: CourseFilterTypes } = {
  SEARCH: {
    filterName: 'search',
    filterOptions: [],
  },
  SUPER_CATEGORY: {
    filterName: 'superCategories',
    filterOptions: ['웹', '모바일 앱', 'AI', '데이터', '데브옵스', '게임'],
  },
  SUB_CATEGORY: {
    filterName: 'subCategories',
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
  COST_TYPE: { filterName: 'isFree', filterOptions: ['무료', '무료(국비)'], isMore: false },
  COST_INPUT: {
    filterName: 'costInput',
    filterOptions: [],
    minValue: 0,
    maxValue: 1000,
    step: 50,
    unit: '만원 이하',
  },
  PERIOD: { filterName: 'period', filterOptions: [], isMore: false },
  PERIOD_INPUT: {
    filterName: 'periodInput',
    filterOptions: [],
    minValue: 0,
    maxValue: 12,
    step: 1,
    unit: '개월 이하',
  },
  TEST: { filterName: 'isTested', filterOptions: ['있음', '없음'], isMore: false },
};

export interface CourseFilterTypes {
  filterName: string;
  filterOptions: string[];
  isMore?: boolean;
  minValue?: number;
  maxValue?: number;
  step?: number;
  unit?: string;
}

export const 전체 = '전체';
export const 자유 = '자유';
export const 부트캠프질문 = '부트캠프 질문';
export const 개발질문 = '개발 질문';

export const POST_FILTERS: { [key: string]: PostFilterTypes } = {
  SEARCH: {
    filterName: 'search',
    filterOptions: [],
  },
  TOPIC: {
    filterName: 'topic',
    filterOptions: [자유, 부트캠프질문, 개발질문],
  },
  TAG: {
    filterName: 'tag',
    filterOptions: [],
  },
};

interface PostFilterTypes {
  filterName: string;
  filterOptions: string[];
}
