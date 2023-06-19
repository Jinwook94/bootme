import styled from 'styled-components';

export const SideTabWrapper1 = styled.div`
  display: block;
  width: 312px;

  @media (max-width: 960px) {
    display: none;
  }
`;

export const SideTabWrapper2 = styled.div`
  display: block;
  height: fit-content;
  background-color: #ffffff;
  border-radius: 4px;
  border: 1px solid #f2f2f2;
  padding: 1rem;
  margin-left: 24px;
  margin-bottom: 10px;
`;

export const TopicTitle = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-right: 1rem;
`;

export const TopicText = styled.div`
  color: rgb(34, 34, 34);
  font-size: 16px;
  font-weight: 600;
  line-height: 20px;
`;

export const ResetIconWrapper = styled.div`
  cursor: pointer;
  position: relative;
  width: 100%;
  height: 100%;
  @media (max-width: 768px) {
    display: inline-block;
  }

  &:hover {
    &:before {
      content: '';
      position: absolute;
      top: -8px;
      left: -8px;
      width: 35px;
      height: 35px;
      border-radius: 50%;
      background-color: #f2f2f2;
      z-index: 1;
    }
  }
  svg {
    position: relative;
    width: 1.2rem;
    height: 1.2rem;
    z-index: 2;
  }
`;

export const TopicWrapper = styled.div`
  flex: 1;
  flex-direction: column;
  display: flex;
  box-sizing: border-box;
  margin-top: 1rem;
`;

export const Topic = styled.div`
  align-items: center;
  flex-direction: row;
  display: flex;
  box-sizing: border-box;
  font-weight: 400;
  font-size: 16px;
  line-height: 24px;
  color: #21293c;
  cursor: pointer;
  margin-right: 1rem;
  margin-bottom: 8px;

  &:hover {
    background-color: #efefef;
    border-radius: 4px;
  }
`;

export const TopicEmoji = styled.div`
  width: 36px;
  height: 36px;
  background-color: #f5f5f7;
  border-radius: 5px;
  vertical-align: bottom;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const TopicName = styled.div`
  margin-left: 0.5rem;
  font-size: 16px;
  line-height: 24px;
  font-weight: 500;
`;

export const WriteButton = styled.div`
  margin-left: 24px;
`;
