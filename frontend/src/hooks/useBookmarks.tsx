import React, { createContext, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useLogin } from './useLogin';
import { useSnackbar } from './useSnackbar';
import SNACKBAR_MESSAGE, { CHECK, EXCLAMATION } from '../constants/snackbar';
import { BOOKMARK_TYPE } from '../constants/others';
import { COURSE_BOOKMARKED, COURSE_CLICKED, POST_BOOKMARKED, POST_CLICKED } from '../constants/webhook';
import useWebhook from './useWebhook';

const BookmarkContext = createContext<BookmarkContextProps>({
  handleBookmarkClick: async () => {},
  handleBookmark: async () => Promise.resolve(),
  itemCount: 0,
  currentItems: [],
  fetchBookmarkedItems: () => Promise.resolve(),
});

export const BookmarkProvider = ({ children }: BookmarkProviderProps) => {
  const { isLogin } = useLogin();
  const { showSnackbar } = useSnackbar();
  const { sendWebhookNoti } = useWebhook();
  const memberId = localStorage.getItem('memberId');
  const [itemCount, setItemCount] = useState<number>();
  const [currentItems, setCurrentItems] = useState<Course[]>([]);

  const handleBookmarkClick = async (
    id: number,
    type: BookmarkType,
    bookmarkedState: boolean,
    setBookmarkedState: React.Dispatch<boolean>
  ) => {
    const eventTypes: Record<BookmarkType, { clicked: string; bookmarked: string }> = {
      [BOOKMARK_TYPE.COURSE]: { clicked: COURSE_CLICKED, bookmarked: COURSE_BOOKMARKED },
      [BOOKMARK_TYPE.POST]: { clicked: POST_CLICKED, bookmarked: POST_BOOKMARKED },
    };
    const eventType = eventTypes[type];

    if (isLogin) {
      handleBookmark(id, type, bookmarkedState).then(() => {
        setBookmarkedState(!bookmarkedState);
      });
      sendWebhookNoti(eventType.clicked, id);
      sendWebhookNoti(eventType.bookmarked, id);
    } else {
      showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, EXCLAMATION);
    }
  };

  const handleBookmark = async (id: number, type: BookmarkType, isBookmarked: boolean) => {
    const typePaths: Record<BookmarkType, string> = {
      [BOOKMARK_TYPE.COURSE]: 'courses',
      [BOOKMARK_TYPE.POST]: 'posts',
      [BOOKMARK_TYPE.COMMENT]: 'comments',
    };

    const path = typePaths[type];
    const endpoint = `/bookmarks/${memberId}/${path}/${id}`;
    const method = isBookmarked ? 'DELETE' : 'POST';
    if (isLogin) {
      try {
        await fetcher(endpoint, { method: method });
        if (method === 'DELETE') {
          showSnackbar(SNACKBAR_MESSAGE.SUCCESS_DELETE_BOOKMARK, CHECK);
        } else {
          showSnackbar(SNACKBAR_MESSAGE.SUCCESS_ADD_BOOKMARK, CHECK);
        }
        return Promise.resolve();
      } catch (e: any) {
        if (e.response.data) {
          showSnackbar(SNACKBAR_MESSAGE.FAIL_ADD_BOOKMARK + ': ' + e.response.data.message, EXCLAMATION);
        }
        return Promise.reject();
      }
    } else if (!isLogin) {
      showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, EXCLAMATION);
    }
  };

  const fetchBookmarkedItems = (type: BookmarkType, page: number, size: number) => {
    const typePaths: Record<BookmarkType, string> = {
      [BOOKMARK_TYPE.COURSE]: 'courses',
      [BOOKMARK_TYPE.POST]: 'posts',
      [BOOKMARK_TYPE.COMMENT]: 'comments',
    };

    const path = typePaths[type];

    return fetcher
      .get(`/bookmarks/${memberId}/${path}`, {
        params: {
          page: page,
          size: size,
        },
      })
      .then(r => {
        setItemCount(r.data.totalElements);
        setCurrentItems(r.data.content);
      })
      .catch(e => {
        return Promise.reject(e);
      });
  };

  return (
    <BookmarkContext.Provider
      value={{
        handleBookmarkClick,
        handleBookmark,
        itemCount,
        currentItems,
        fetchBookmarkedItems,
      }}
    >
      {children}
    </BookmarkContext.Provider>
  );
};

export const useBookmarks = () => useContext(BookmarkContext);

interface BookmarkContextProps {
  handleBookmarkClick: (
    id: number,
    type: BookmarkType,
    bookmarkedState: boolean,
    setBookmarkedState: React.Dispatch<boolean>
  ) => void;
  handleBookmark: (id: number, type: BookmarkType, bookmarked: boolean) => Promise<void>;
  itemCount: number | undefined;
  currentItems: any[];
  fetchBookmarkedItems: (type: BookmarkType, page: number, size: number) => Promise<void>;
}

export type BookmarkTypeKey = 'COURSE' | 'POST' | 'COMMENT';
export type BookmarkType = typeof BOOKMARK_TYPE[BookmarkTypeKey];

interface BookmarkProviderProps {
  children: React.ReactNode;
}
