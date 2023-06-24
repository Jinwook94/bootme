import { BookmarkTypeKey } from '../hooks/useBookmarks';

export const BOOTME_URL = 'https://bootme.co.kr';

export const GOOGLE = 'google';
export const NAVER = 'naver';
export const KAKAO = 'kakao';

export const UTM_PARAMS = 'utm_source=bootme&utm_medium=referral';

export const SORT_OPTION: { [type: string]: SortOption } = {
  CREATED_AT: 'createdAt',
  CLICKS: 'clicks',
  BOOKMARKS: 'bookmarks',
  LIKES: 'likes',
};

export const VOTABLE_TYPE: { [type: string]: VotableType } = {
  POST: 'POST',
  POST_COMMENT: 'POST_COMMENT',
};

export const VOTE_TYPE: { [type: string]: VoteType } = {
  NONE: 'NONE',
  UPVOTE: 'UPVOTE',
  DOWNVOTE: 'DOWNVOTE',
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
