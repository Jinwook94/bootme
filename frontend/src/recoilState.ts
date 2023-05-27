import { atom } from 'recoil';
import { HOME } from './constants/pages';

export const currentView = atom({
  key: 'currentViewState',
  default: HOME,
});

export const currentPageHome = atom({
  key: 'currentPageHome',
  default: 1,
});

export const currentPageBookmark = atom({
  key: 'currentPageBookmark',
  default: 1,
});
