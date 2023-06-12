import ShareButton from '../ShareButton';
import { BookmarkIcon2 } from '../../../constants/icons';
import React from 'react';
import { useBookmarks } from '../../../hooks/useBookmarks';
import { BookmarkButton, LargeScreen, Mobile } from './style';
import { Tooltip } from 'antd';

const Buttons = ({ course }: { course: Course | undefined }) => {
  const { handleBookmark } = useBookmarks();
  return (
    <>
      <LargeScreen>
        <Tooltip placement="topRight" title={'북마크 저장'}>
          <BookmarkButton
            onClick={() => {
              handleBookmark(course ? course.id : 0);
            }}
          >
            <BookmarkIcon2 />
          </BookmarkButton>
        </Tooltip>
      </LargeScreen>
      <Mobile>
        <BookmarkButton
          onClick={() => {
            handleBookmark(course ? course.id : 0);
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
