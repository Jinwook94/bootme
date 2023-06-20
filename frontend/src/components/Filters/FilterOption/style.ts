import styled from 'styled-components';

export const ListItem = styled.li<{ borderTop: boolean | undefined }>`
  cursor: pointer;
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
  justify-content: center;
  align-items: center;
  color: rgb(38, 55, 71);
  font-size: 0.875rem;
  cursor: pointer;
  position: relative;
  line-height: 150%;
  word-break: keep-all;
`;

export const Option = styled.div`
  display: flex;
  gap: 0.375rem;
  -webkit-box-align: center;
  align-items: center;
`;

export const ModalWrapper = styled.div`
  padding: 10px 0 10px 10px !important;
  display: flex !important;
  cursor: pointer !important;
`;

export const Name = styled.span`
  -webkit-box-pack: center !important;
  -webkit-box-direction: normal !important;
  -webkit-box-orient: vertical !important;
  display: flex !important;
  flex-direction: column !important;
  justify-content: center !important;
  flex: 1 1 auto !important;
  width: 100% !important;
  font-size: 1rem !important;
  line-height: 20px !important;
  color: #4f4f4f;
  font-weight: 400 !important;

  ${ModalWrapper}:hover & {
    color: black;
    font-weight: 400 !important;
  }
`;

export const CheckBoxWrapper1 = styled.div`
  margin-left: 16px !important;
  cursor: pointer !important;
`;

export const CheckBoxWrapper2 = styled.span`
  position: relative !important;
  display: inline-block !important;
  cursor: pointer !important;
  padding: 0 !important;
`;

export const ModalFilterCheckbox = styled.input.attrs({
  type: 'checkbox',
})`
  border: 0 !important;
  clip: unset !important;
  clip-path: unset !important;
  height: 1px !important;
  overflow: clip !important;
  padding: 0 !important;
  position: absolute !important;
  white-space: nowrap !important;
  width: 1px !important;
  opacity: 0 !important;
  margin: 0 !important;
`;

export const Unchecked = styled.span`
  display: inline-block !important;
  border-width: 1px !important;
  border-style: solid !important;
  border-color: rgb(176, 176, 176) !important;
  height: 24px !important;
  width: 24px !important;
  background: rgb(255, 255, 255) !important;
  text-align: center !important;
  overflow: hidden !important;
  vertical-align: top !important;
  border-radius: 4px !important;

  ${ModalWrapper}:hover & {
    border-color: rgb(34, 34, 34) !important;
  }
`;

export const CheckedWrapper1 = styled.span`
  display: inline-block !important;
  border-width: 1px !important;
  border-style: solid !important;
  height: 24px !important;
  width: 24px !important;
  text-align: center !important;
  overflow: hidden !important;
  vertical-align: top !important;
  border-radius: 4px !important;
  background: rgb(34, 34, 34) !important;
  color: rgb(255, 255, 255) !important;
  border-color: rgb(34, 34, 34) !important;
`;

export const CheckedWrapper2 = styled.span`
  -webkit-box-pack: center !important;
  -webkit-box-align: center !important;
  display: block !important;
  align-items: center !important;
  justify-content: center !important;
  width: 100% !important;
  height: 100% !important;
  margin-top: 2px !important;
  margin-left: 3px !important;
  color: rgb(255, 255, 255) !important;
`;
