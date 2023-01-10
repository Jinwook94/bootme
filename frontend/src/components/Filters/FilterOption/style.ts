import styled from 'styled-components';

export const ListItem = styled.li<{ borderTop: boolean | undefined }>`
  padding: 0.1875rem 0;
  letter-spacing: -0.009em;
  line-height: 1.6;
  list-style: none;
  border-top: ${props => (props.borderTop === true ? '0.0625rem solid rgb(215,226,235)' : 0)};
  margin-top: ${props => (props.borderTop === true ? '0.5rem' : 0)};
  padding-top: ${props => (props.borderTop === true ? '0.5rem' : 0)};
`;

export const Wrapper = styled.div`
  display: flex;
  color: rgb(38, 55, 71);
  font-size: 0.875rem;
  cursor: pointer;

  @media (max-width: 1200px) {
    font-size: 12px;
  }
`;

export const BoxWrapper = styled.div`
  display: flex;
  color: rgb(38, 55, 71);
  font-size: 0.875rem;
  cursor: pointer;
  position: relative;
  line-height: 150%;
  word-break: keep-all;
`;

export const Checkbox = styled.input.attrs({
  type: 'checkbox',
})`
  position: relative;
  top: 0.15625rem;
  width: 1rem;
  height: 1rem;
  border: 0.125rem solid rgb(178, 192, 204);
  background-color: transparent;
  border-radius: 0.25rem;
  outline: none;
  cursor: pointer;
  flex-shrink: 0;
  margin-right: 0.375rem;
`;

export const Option = styled.div`
  display: flex;
  gap: 0.375rem;
  -webkit-box-align: center;
  align-items: center;
`;
