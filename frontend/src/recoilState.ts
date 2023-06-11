import { atom } from 'recoil';

export const currentPageCourseList = atom({
  key: 'currentPageCourseList',
  default: 1,
});

export const currentPageBookmark = atom({
  key: 'currentPageBookmark',
  default: 1,
});
