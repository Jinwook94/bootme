import styled from 'styled-components';

export const BottomTapBarWrapper = styled.div`
  display: none;

  @media (max-width: 640px) {
    position: fixed;
    bottom: 0;
    display: flex;
    justify-content: space-around;
    align-items: center;
    background: #ffffff;
    color: rgb(34, 34, 34);
    width: 100%;
    height: 60px;
    border-top: 3px solid rgb(242, 242, 242);
  }
`;

export const TapBarItem = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
`;

export const TapBarItemIcon = styled.div`
  width: 100%;
  height: 100%;
  position: relative;
  margin-top: 6px;
  span {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  svg {
    fill: #878a8c;
    width: 24px;
    height: 100%;
  }
`;

export const TapBarItemText = styled.div`
  color: #878a8c;
  font-size: 10px;
  padding-bottom: 6px;
`;

export const ColoredIcon = styled(TapBarItemIcon)`
  svg {
    fill: #1677ff;
  }
`;

export const ColoredText = styled(TapBarItemText)`
  color: #1677ff;
`;
