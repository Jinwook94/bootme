import ReactModal from 'react-modal';
import React from 'react';
import './style.css';
import { useFilters } from '../../../hooks/useFilters';

const ModalFilter = () => {
  const { isModal, handleModal } = useFilters();
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
          padding: '20px',
        },
      }}
    >
      <span> 컨텐츠 </span>
      <button onClick={handleModal}>닫기</button>
    </ReactModal>
  );
};

export default ModalFilter;
