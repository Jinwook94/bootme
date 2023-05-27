import ShareButton from '../ShareButton';
import { BookmarkIcon2 } from '../../../constants/icons';
import React from 'react';
import { useBookmarks } from '../../../hooks/useBookmarks';
import { BookmarkButton } from './style';

const Buttons = ({ course }: { course: Course | undefined }) => {
  const { handleBookmark } = useBookmarks();
  return (
    <>
      <BookmarkButton
        onClick={() => {
          handleBookmark(course ? course.id : 0);
        }}
      >
        <BookmarkIcon2 />
      </BookmarkButton>
      <ShareButton course={course} />
    </>
  );
};

export default Buttons;
