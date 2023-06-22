import ShareButton from '../ShareButton';
import { BookmarkIcon2 } from '../../../constants/icons';
import React from 'react';
import { useBookmarks } from '../../../hooks/useBookmarks';
import { BookmarkButton, LargeScreen, Mobile } from './style';
import { Tooltip } from 'antd';
import { BOOKMARK_TYPE } from '../../../constants/others';

const Buttons = ({ course, bookmarkedState, setBookmarkedState }: ButtonsProps) => {
  const { handleBookmarkClick } = useBookmarks();
  return (
    <>
      <LargeScreen>
        <Tooltip placement="topRight" title={'북마크 저장'}>
          <BookmarkButton
            onClick={() => {
              handleBookmarkClick(course ? course.id : 0, BOOKMARK_TYPE.COURSE, bookmarkedState, setBookmarkedState);
            }}
          >
            <BookmarkIcon2 />
          </BookmarkButton>
        </Tooltip>
      </LargeScreen>
      <Mobile>
        <BookmarkButton
          onClick={() => {
            handleBookmarkClick(course ? course.id : 0, BOOKMARK_TYPE.COURSE, bookmarkedState, setBookmarkedState);
          }}
        >
          <BookmarkIcon2 />
        </BookmarkButton>
      </Mobile>
      <ShareButton course={course} />
    </>
  );
};

export default Buttons;

interface ButtonsProps {
  course: Course | undefined;
  bookmarkedState: boolean;
  setBookmarkedState: React.Dispatch<boolean>;
}
