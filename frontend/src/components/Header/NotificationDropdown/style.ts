import styled from 'styled-components';

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 340px;

  @media (max-width: 575px) {
    width: 300px;
  }
`;

export const Title = styled.h3`
  padding: 6px 16px 12px 16px;
  font-size: 16px;
  border-bottom: 1px solid #e4e1e1;
`;

export const NotificationItemWrapper = styled.a`
  border-radius: 0.5rem;
  padding: 12px 18px;

  &:hover {
    border-radius: 0.5rem;
    background-color: #fbfbfb;
    cursor: pointer;
  }
`;

export const ItemWrapper = styled.div``;

export const NotificationDate = styled.div`
  font-size: 13px;
  color: rgb(136, 136, 136);
  text-align: right;
`;

export const NoResult = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100px;
  font-weight: 500;
`;
