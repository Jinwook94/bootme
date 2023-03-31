import styled from 'styled-components';

export const Pagination = styled.ul`
  display: flex;
  justify-content: center;
  list-style: none;
  padding-left: 0;
  margin: 0;
  border-radius: 0.25rem;

  @media (max-width: 575px) {
    margin-top: calc(2.5rem - ((2.5rem / 8) * 4)) !important;
  }

  @media (min-width: 576px) and (max-width: 767px) {
    margin-top: calc(2.5rem - ((2.5rem / 8) * 2)) !important;
  }

  @media (min-width: 768px) {
    margin-top: 2.5rem !important;
  }
`;

export const PageItem = styled.li`
  margin-top: 0;
  letter-spacing: -0.009em;
  line-height: 1.6;
  list-style: none;
  cursor: pointer;

  @media (max-width: 767px) {
    font-size: 15px;
  }
`;

export const PageNumb = styled.a`
  position: relative;
  padding: 0.25rem 0.75rem;
  border: 0;
  margin-left: 0;
  background-color: rgba(50, 50, 124, 0.08);
  color: #263747;
  font-weight: 500;
  text-decoration: none;
  display: block;
  line-height: 1.25;

  &.active {
    background-color: #0078ff !important;
    color: white !important;
    pointer-events: none;
    cursor: default;
  }

  &:hover {
    background-color: rgba(50, 50, 124, 0.16);
  }

  @media (max-width: 575px) {
    padding: 4px 10px;
    font-size: 12px;
    line-height: 1.5;
  }
`;
