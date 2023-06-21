import { BookmarkTypeKey } from '../hooks/useBookmarks';

export const BOOTME_URL = 'https://bootme.co.kr';

export const GOOGLE = 'google';
export const NAVER = 'naver';
export const KAKAO = 'kakao';

export const UTM_PARAMS = 'utm_source=bootme&utm_medium=referral';

export const VOTABLE_TYPE = {
  POST: 'post',
  POST_COMMENT: 'postComment',
};

export const VOTE_TYPE = {
  NONE: 'none',
  UPVOTE: 'upvote',
  DOWNVOTE: 'downvote',
};

export const IMAGE_TYPE = {
  COURSE_DETAIL: 'courseDetail',
  POST: 'post',
  POST_COMMENT: 'postComment',
};

export const BOOKMARK_TYPE: Record<BookmarkTypeKey, string> = {
  COURSE: 'course',
  POST: 'post',
  COMMENT: 'comment',
} as const;
