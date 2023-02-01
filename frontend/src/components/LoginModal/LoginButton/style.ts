import styled from 'styled-components';

export const Wrapper1 = styled.div`
  width: 300px;
  max-width: 400px;
  min-width: min-content;
  margin-top: 10px;
  border-radius: 4px;
  box-sizing: border-box;
  transition: background-color 0.218s, border-color 0.218s;
  -webkit-user-select: none;
  -webkit-appearance: none;
  background-color: #fff;
  background-image: none;
  border: 1px solid #dadce0;
  color: #3c4043;
  cursor: pointer;
  font-size: 14px;
  height: 40px;
  letter-spacing: 0.25px;
  outline: none;
  overflow: hidden;
  padding: 0 12px;
  position: relative;
  text-align: center;
  vertical-align: middle;
  white-space: nowrap;
  &:hover {
    background-color: rgb(249, 251, 255);
  }
`;

export const Wrapper2 = styled.div`
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: space-between;
  flex-wrap: nowrap;
  height: 100%;
  position: relative;
  width: 100%;
`;

export const IconWrapper = styled.div`
  height: 23px;
  width: 23px;
  margin-right: 8px;
  min-width: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const LoginText = styled.span`
  flex-grow: 1;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  vertical-align: top;
`;
