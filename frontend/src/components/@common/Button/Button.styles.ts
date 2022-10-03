import styled from "styled-components";

export const ButtonStyle = styled.button`
  width: fit-content;
  padding: 0.75rem 1.4375rem;
  border: none;
  border-radius: 0.625rem;
  background-color: ${(props) => props.theme.colors.BLUE_600};
  font-size: 1.25rem;
  font-weight: 500;
  color: ${(props) => props.theme.colors.WHITE};
  white-space: pre;
  :hover {
    opacity: 70%;
  }
  @media (max-width: 520px) {
    padding: 0.625rem 0.75rem;
    font-size: 0.875rem;
  }
`;