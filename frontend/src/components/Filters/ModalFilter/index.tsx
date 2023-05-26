import React from 'react';
import ReactModal from 'react-modal';
import { useFilters } from '../../../hooks/useFilters';
import './style.css';
import { HeaderText, IconWrapper, ModalBody, ModalFooter, ModalHeader, ResetFilter, ShowCourse } from './style';
import { CloseIcon } from '../../../constants/icons';
import { COURSE_FILTERS } from '../../../constants/courseFilter';
import ModalFilterItem from '../ModalFilterItem';
import { useCourses } from '../../../hooks/useCourses';

const ModalFilter = () => {
  const { isModal, handleModal, isReset, resetFilters } = useFilters();
  const { courseCount } = useCourses();

  return (
    <ReactModal
      id={'FilterModal'}
      isOpen={isModal}
      onRequestClose={handleModal}
      shouldFocusAfterRender
      shouldCloseOnOverlayClick
      shouldCloseOnEsc
      preventScroll
      closeTimeoutMS={300}
      style={{
        overlay: {
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: 'rgba(118, 118, 118, 0.9)',
        },
        content: {
          position: 'absolute',
          top: '12px',
          left: '0',
          right: '0',
          bottom: '0',
          border: '1px solid #ccc',
          background: '#fff',
          overflow: 'auto',
          WebkitOverflowScrolling: 'touch',
          borderRadius: '4px',
          outline: 'none',
          padding: '0',
        },
      }}
    >
      <ModalHeader>
        <div></div>
        <IconWrapper onClick={handleModal}>
          <CloseIcon />
        </IconWrapper>
        <HeaderText>
          <span> 필터 </span>
        </HeaderText>
        <div></div>
      </ModalHeader>
      <ModalBody>
        <ModalFilterItem
          filterName={'개발 분야'}
          filterOptions={[...COURSE_FILTERS.SUPER_CATEGORY.filterOptions, ...COURSE_FILTERS.SUB_CATEGORY.filterOptions]}
          isMore={true}
          isReset={isReset}
        />
        <ModalFilterItem
          filterName={'기술 스택'}
          filterOptions={[...COURSE_FILTERS.LANGUAGES.filterOptions, ...COURSE_FILTERS.FRAMEWORKS.filterOptions]}
          isMore={true}
          isReset={isReset}
        />
        <ModalFilterItem
          filterName={'수강 비용'}
          filterOptions={COURSE_FILTERS.COST_TYPE.filterOptions}
          isMore={false}
          isReset={isReset}
        />
        <ModalFilterItem
          filterName={'수강 기간'}
          filterOptions={COURSE_FILTERS.PERIOD.filterOptions}
          isMore={false}
          isReset={isReset}
        />
        <ModalFilterItem
          filterName={'코딩 테스트'}
          filterOptions={COURSE_FILTERS.TEST.filterOptions}
          isMore={false}
          isReset={isReset}
        />
      </ModalBody>
      <ModalFooter>
        <ResetFilter onClick={resetFilters}>전체 해제</ResetFilter>
        <ShowCourse onClick={handleModal}>코스 {courseCount}개 표시</ShowCourse>
      </ModalFooter>
    </ReactModal>
  );
};

export default ModalFilter;
