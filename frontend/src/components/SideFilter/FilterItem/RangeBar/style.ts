import styled from 'styled-components';

export const Wrapper1 = styled.div`
  margin: 0.5rem 0.5rem 0 0.5rem;
`;

export const BarWrapper = styled.div`
  padding: 0 0.5rem;
`;

export const Bar = styled.input.attrs({
  type: 'range',
  className: 'form-range',
  id: 'customRange3',
})`
  width: 90%;
`;

export const Wrapper2 = styled.div`
  display: flex;
  -webkit-box-pack: justify;
  justify-content: flex-start;
  position: relative;
`;

export const InputWrapper = styled.div`
  height: 2rem;
  position: relative;
  width: 4rem;
  margin-top: 0.3rem;
  background: rgb(251, 251, 253);
  border: 0.0625rem solid rgb(230, 238, 245);
  border-radius: 0.3125rem;
  color: rgb(38, 55, 71);

  @media (max-width: 1200px) {
    width: 4rem;
  }
`;

export const Input = styled.input.attrs({ type: 'text', inputmode: 'numeric' })`
  padding: 0.6875rem 0.75rem;
  font-size: 1rem;
  color: dimgray;
  text-align: right;
  width: 100%;
  height: 100%;
  background: transparent;
  border: none;
  outline: none;
`;

export const Unit = styled.span`
  display: inline-block;
  margin-top: 0.8rem;
  margin-left: 0.3rem;
  vertical-align: -0.5rem;
  color: rgb(150, 150, 150);
  font-size: 0.85rem;
`;
