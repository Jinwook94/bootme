import { atom } from 'recoil';

export const currentPageHome = atom({
  key: 'currentPageHome',
  default: 1,
});

export const currentPageBookmark = atom({
  key: 'currentPageBookmark',
  default: 1,
});
