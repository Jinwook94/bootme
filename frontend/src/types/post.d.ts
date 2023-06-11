export interface PostListResponse {
  content: Post[];
  pageable: Pageable;
  totalElements: number;
  totalPages: number;
  last: boolean;
  numberOfElements: number;
  size: number;
  first: boolean;
  number: number;
  sort: Sort;
  empty: boolean;
}

export interface Post {
  id: number;
  writerId: number;
  writerNickname: string;
  writerProfileImage: string;
  topic: string;
  title: string;
  contentExcerpt: string;
  likes: number;
  views: number;
  bookmarks: number;
  status: string;
  createdAt: number;
  modifiedAt: number;
  commentCount: number;
  voted: string;
}

export interface PostDetail extends Omit<Post, 'contentExcerpt'> {
  content: string;
}

interface PostComment {
  id: number;
  postId: number;
  writerId: number;
  writerNickname: string;
  writerProfileImage: string;
  parentId: number;
  content: string;
  groupNum: number;
  levelNum: number;
  orderNum: number;
  likes: number;
  voted: string;
  createdAt: number;
  modifiedAt: number;
}
