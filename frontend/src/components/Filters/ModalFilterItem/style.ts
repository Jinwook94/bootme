import styled from 'styled-components';

export const Wrapper = styled.div`
  padding: 24px 0 16px 0;
  border-bottom: 1px solid rgb(235, 235, 235) !important;
  height: 100%;
  overflow: hidden;
`;

export const Title = styled.div`
  color: rgb(34, 34, 34) !important;
  font-weight: 500 !important;
  font-size: 18px !important;
  line-height: 22px !important;
  padding-bottom: 8px !important;
`;

export const MoreButton = styled.button`
  padding: 8px 0 8px 10px;
  background: none;
  height: 1rem;
  color: rgb(34, 34, 34) !important;
  text-decoration: underline !important;
  font-weight: 500 !important;
  outline: none !important;
  user-select: auto !important;
`;

export const MoreOptions = styled.div<{ isMoreOpen: boolean }>`
  transition: opacity 0.5s ease-out;
  opacity: ${props => (props.isMoreOpen ? 1 : 0)};
`;
