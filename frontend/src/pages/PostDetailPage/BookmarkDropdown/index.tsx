import { Popover } from 'antd';
import { BookmarkedIcon, BookmarkIcon, ThreeDotsVerticalIcon } from '../../../constants/icons';
import React, { useState } from 'react';
import { BOOKMARK_TYPE } from '../../../constants/others';
import { POST_BOOKMARKED, POST_CLICKED } from '../../../constants/webhook';
import { useBookmarks } from '../../../hooks/useBookmarks';
import useWebhook from '../../../hooks/useWebhook';
import { LinkItem, ThreeDotsWrapper, Items, Item, IconWrapper } from './stlye';

export const BookmarkDropdown = ({ postId, bookmarked }: BookmarkDropdownProps) => {
  const { sendWebhookNoti } = useWebhook();
  const { handleBookmark } = useBookmarks();
  const [bookmarkedState, setBookmarkedState] = useState(bookmarked);
  const [visible, setVisible] = useState(false);

  const handleVisibleChange = (flag: boolean) => {
    setVisible(flag);
  };

  const stopPropagation = (event: React.MouseEvent) => {
    event.stopPropagation();
    event.preventDefault();
  };

  const items = () => {
    return (
      <>
        <Items>
          <Item>
            <LinkItem
              onClick={event => {
                event.stopPropagation();
                event.preventDefault();
                handleBookmark(postId, BOOKMARK_TYPE.POST, bookmarkedState).then(() => {
                  setBookmarkedState(!bookmarkedState);
                });
                sendWebhookNoti(POST_CLICKED, postId);
                sendWebhookNoti(POST_BOOKMARKED, postId);
              }}
            >
              <IconWrapper>{bookmarkedState ? <BookmarkedIcon /> : <BookmarkIcon />}</IconWrapper>
              북마크
            </LinkItem>
          </Item>
        </Items>
      </>
    );
  };

  return (
    <Popover content={items} trigger="click" placement="bottomRight" open={visible} onOpenChange={handleVisibleChange}>
      <ThreeDotsWrapper onClick={stopPropagation}>
        <ThreeDotsVerticalIcon />
      </ThreeDotsWrapper>
    </Popover>
  );
};

interface BookmarkDropdownProps {
  postId: number;
  bookmarked: boolean;
}
