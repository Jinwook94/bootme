import styled from 'styled-components';

export const SelectStyle = styled.select`
  -webkit-text-size-adjust: 100%;
  -webkit-tap-highlight-color: transparent;
  -webkit-appearance: none;
  display: inline-block;
  text-align: center;
  vertical-align: middle;
  user-select: none;
  border-radius: 0.25rem;
  border: 1px solid transparent;
  background-color: transparent;
  font-weight: 500;
  transition: color 0.08s ease-in-out, background-color 0.08s ease-in-out, border-color 0.08s ease-in-out,
    box-shadow 0.08s ease-in-out;
  padding: 0.3125rem 0.8125rem;
  font-size: 0.8125rem;
  line-height: 1.125rem;
  border-color: #d7e2eb;
  color: #263747;
  padding-right: 1.5rem;
  background-image: url(https://career.programmers.co.kr/assets/toggle-black-3ebb19a240c1ef57dac0b24e19fd00eff32a7e32ff8f2b87cfa2eb399c193c3a.png);
  background-position: calc(100% - 0.5rem) 49%;
  background-repeat: no-repeat;
  background-size: 0.625rem 0.3125rem;
  cursor: pointer;
  width: 5.438rem;

  @media (max-width: 767px) {
    font-size: 0.8125rem;
    line-height: 1.125rem;
  }
`;
