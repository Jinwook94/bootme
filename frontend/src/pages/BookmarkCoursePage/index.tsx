import React, { useEffect, useState } from 'react';
import { createStyles, Navbar, Group, Text, Flex, Container, MediaQuery } from '@mantine/core';
import { BookmarkLayout, NavBarItem, Wrapper } from './style';
import CourseCardList from '../../components/CourseCardList';
import { PaginationWrapper } from '../CourseListPage/style';
import { Pagination } from 'antd';
import { useBookmarks } from '../../hooks/useBookmarks';
import { BOOKMARK_TYPE } from '../../constants/others';
import { useNavigate } from 'react-router-dom';
import PATH from '../../constants/path';
import { PapersIcon, RocketIcon } from '../../constants/icons';
import LoadingSpinner from '../../components/@common/LoadingSpinner';

const useStyles = createStyles(theme => ({
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

const BookmarkCoursePage = () => {
  const navigate = useNavigate();
  const { classes } = useStyles();
  const { fetchBookmarkedItems, itemCount, currentItems } = useBookmarks();
  const [currentPage, setCurrentPage] = useState(1);
  const [size] = useState(12);
  const [isLoading, setIsLoading] = useState(true);

  const handlePageChange = (page: React.SetStateAction<number>) => {
    setCurrentPage(page);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  useEffect(() => {
    fetchBookmarkedItems(BOOKMARK_TYPE.COURSE, currentPage, size).then(() => setIsLoading(false));
  }, [currentPage]);

  return (
    <Wrapper>
      <BookmarkLayout>
        <Flex justify="center" align="flex-start" direction="row">
          <MediaQuery query="(max-width: 75em)" styles={{ display: 'none' }}>
            <Navbar height={230} width={{ sm: 250 }} p="md" className={classes.navbar}>
              <Navbar.Section grow>
                <Group className={classes.header} position="apart">
                  <Text fz={20} ml={16} fw={700}>
                    내 북마크
                  </Text>
                </Group>
                <NavBarItem style={{ backgroundColor: '#f1f3f5', borderRadius: '0.25rem' }}>
                  <RocketIcon />
                  <span>부트캠프</span>
                </NavBarItem>
                <NavBarItem onClick={() => navigate(PATH.BOOKMARK.POST)}>
                  <PapersIcon />
                  <span>커뮤니티</span>
                </NavBarItem>
              </Navbar.Section>
            </Navbar>
          </MediaQuery>
          <Container className={classes.body}>
            <Text fz={20} fw={700} pt={16} pl={16} className={classes.bodyHeaderDesktop}>
              북마크 코스
            </Text>
            <Flex direction={'row'} justify={'space-around'} className={classes.bodyHeaderMobile}>
              <Text
                fz={20}
                fw={700}
                py={16}
                style={{ flex: 1, textAlign: 'center', borderBottom: '2px solid rgb(0, 132, 255)' }}
                className={classes.mobileText}
              >
                코스
              </Text>
              <Text
                fz={20}
                fw={700}
                py={16}
                style={{ flex: 1, textAlign: 'center', borderBottom: '2px solid rgb(242, 242, 242)' }}
                className={classes.mobileText}
                onClick={() => navigate(PATH.BOOKMARK.POST)}
              >
                게시글
              </Text>
            </Flex>
            <Container p={0} style={{ minHeight: '600px' }}>
              {isLoading ? (
                <LoadingSpinner />
              ) : itemCount === 0 ? (
                <Flex justify={'center'} mt={48}>
                  <Text fz="xl" fw={600} color="gray.5">
                    아직 북마크 저장한 코스가 없습니다...
                  </Text>
                </Flex>
              ) : (
                <CourseCardList courses={currentItems} />
              )}
            </Container>
            <PaginationWrapper>
              <Pagination
                current={currentPage}
                pageSize={size}
                total={itemCount}
                onChange={handlePageChange}
                showSizeChanger={false}
              />
            </PaginationWrapper>
          </Container>
        </Flex>
      </BookmarkLayout>
    </Wrapper>
  );
};

export default BookmarkCoursePage;
