import { Layout } from '../../components/@common/Layout';
import styled from 'styled-components';
import { createStyles } from '@mantine/core';

export const useStyles = createStyles(theme => ({
  navbar: {
    backgroundColor: theme.white,
    border: '1px solid #e1e2e3;',
    borderRadius: '5px',

    '@media (max-width: 767px)': {
      borderRadius: '0px',
    },
  },

  header: {
    paddingBottom: theme.spacing.md,
    marginBottom: `calc(${theme.spacing.md} * 1.5)`,
    borderBottom: `1px solid #e1e2e3;`,

    '@media (max-width: 767px)': {
      marginBottom: `0`,
      borderBottom: `4px solid rgb(242, 242, 242)`,
    },
  },

  bodyHeaderDesktop: {
    paddingBottom: theme.spacing.md,
    marginBottom: `calc(${theme.spacing.md} * 1.5)`,
    borderBottom: `1px solid #e1e2e3;`,

    '@media (max-width: 767px)': {
      borderBottom: `4px solid rgb(242, 242, 242)`,
    },
    '@media (max-width: 1200px)': {
      display: 'none',
    },
  },

  bodyHeaderMobile: {
    position: 'relative',
    cursor: 'pointer',
    marginBottom: `0`,

    '@media (min-width: 1200px)': {
      display: 'none',
    },
  },

  mobileText: {
    '&:hover': {
      backgroundColor: '#F8F9FA',
    },
  },

  body: {
    backgroundColor: theme.white,
    border: '1px solid #e1e2e3;',
    borderRadius: '5px',
    minHeight: '800px',
    width: '100%',
    marginLeft: '1rem',
    marginBottom: '3rem',

    '@media (max-width: 1200px)': {
      padding: '0',
    },

    '@media (max-width: 767px)': {
      padding: '0',
      width: '100vw',
      marginLeft: '0',
    },
  },
}));

export const Wrapper = styled.div`
  background-color: #f9fafb;
`;

export const BookmarkLayout = styled(Layout)`
  padding-top: 1.5rem;

  @media (max-width: 767px) {
    padding: 1rem 0 0 0 ;
  },
`;

export const NavBarItem = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0.625rem 0.75rem;
  color: #000;
  font-size: 0.875rem;
  font-weight: 500;
  svg {
    width: 24px;
    height: 24px;
    margin-right: 0.75rem;
  }
`;

export const PostCardList = styled.div`
  min-height: 1000px;
  width: 100%;

  @media (max-width: 640px) {
    min-height: auto;
  }
`;
