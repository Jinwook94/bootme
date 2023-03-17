export const SIDE_FILTER = 'SIDE_FILTER';
export const MODAL_FILTER = 'MODAL_FILTER';

export const CATEGORIES_OPTIONS = {
  SUPER: ['웹', '모바일 앱', 'AI', '데이터', '데브옵스', '게임'],
  SUB: ['프론트엔드', '백엔드', '풀스택', '안드로이드', 'iOS'],
};

export const STACKS_OPTIONS = {
  LANGUAGES: ['JavaScript', 'TypeScript', 'Java', 'Python', 'Swift', 'Kotlin'],
  FRAMEWORKS: ['React', 'Vue.js', 'Spring', 'Node.js', 'Django'],
};

export const CATEGORIES = {
  filterName: '개발 분야',
  filterOptions: [...CATEGORIES_OPTIONS.SUPER, ...CATEGORIES_OPTIONS.SUB],
  isMore: true,
};

export const STACKS = {
  filterName: '기술 스택',
  filterOptions: [...STACKS_OPTIONS.LANGUAGES, ...STACKS_OPTIONS.FRAMEWORKS],
  isMore: true,
};

export const COST_TYPE = { filterName: '수강 비용', filterOptions: ['무료', '무료(국비)'], isMore: false };
export const PERIOD = { filterName: '수강 기간', filterOptions: [], isMore: false };
export const TEST = { filterName: '코딩 테스트', filterOptions: ['있음', '없음'], isMore: false };

export const COST_INPUT = {
  filterName: '비용',
  minValue: 0,
  maxValue: 1000,
  step: 50,
  unit: '만원 이하',
};
export const PERIOD_INPUT = {
  filterName: '수강 기간',
  minValue: 0,
  maxValue: 12,
  step: 1,
  unit: '개월 이하',
};

export const COURSE_FILTERS: { [key: string]: CourseFilterTypes } = {
  CATEGORIES,
  STACKS,
  COST_TYPE,
  PERIOD,
  TEST,
};

export interface CourseFilterTypes {
  filterName: string;
  filterOptions: string[];
  isMore: boolean;
}
