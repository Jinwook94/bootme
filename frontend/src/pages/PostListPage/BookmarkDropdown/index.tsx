import { Popover } from 'antd';
import { BookmarkedIcon, BookmarkIcon, ThreeDotsVerticalIcon } from '../../../constants/icons';
import React, { useState } from 'react';
import { BOOKMARK_TYPE } from '../../../constants/others';
import { useBookmarks } from '../../../hooks/useBookmarks';
import { LinkItem, ThreeDotsWrapper, Items, Item, IconWrapper } from './stlye';

export const BookmarkDropdown = ({ postId, bookmarkedState, setBookmarkedState }: BookmarkDropdownProps) => {
  const { handleBookmarkClick } = useBookmarks();
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
                handleBookmarkClick(postId, BOOKMARK_TYPE.POST, bookmarkedState, setBookmarkedState);
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
  bookmarkedState: boolean;
  setBookmarkedState: React.Dispatch<boolean>;
}
