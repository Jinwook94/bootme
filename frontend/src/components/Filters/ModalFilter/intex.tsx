import ReactModal from 'react-modal';
import { useFilters } from '../../../hooks/useFilters';
import React from 'react';
import './style.css';
import { HeaderText, IconWrapper, ModalBody, ModalFooter, ModalHeader, ResetFilter, ShowCourse } from './style';
import { CloseIcon } from '../../../constants/icons';
import ModalFilterItem from '../ModalFilterItem';
import { COURSE_FILTERS } from '../../../constants/courseFilter';

const ModalFilter = () => {
  const { isModal, handleModal, resetFilters, isReset } = useFilters();
  ReactModal.setAppElement('#root');

  return (
    <ReactModal
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
        {Object.values(COURSE_FILTERS).map(filterGroup => (
          <ModalFilterItem
            key={filterGroup.filterName}
            filter={filterGroup}
            filterName={filterGroup.filterName}
            filterOptions={filterGroup.filterOptions}
            isMore={filterGroup.isMore}
            isReset={isReset}
          />
        ))}
      </ModalBody>
      <ModalFooter>
        <ResetFilter onClick={resetFilters}>전체 해제</ResetFilter>
        <ShowCourse onClick={handleModal}>코스 N개 표시</ShowCourse>
      </ModalFooter>
    </ReactModal>
  );
};

export default ModalFilter;
