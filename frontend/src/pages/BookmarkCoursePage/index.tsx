import React, { useEffect, useState } from 'react';
import { createStyles, Navbar, Group, getStylesRef, Text, Flex, Container, MediaQuery } from '@mantine/core';
import { IconRocket, IconNews } from '@tabler/icons-react';
import { BookmarkLayout, Wrapper } from './style';
import CourseCardList from '../../components/CourseCardList';
import { PaginationWrapper } from '../CourseListPage/style';
import { Pagination } from 'antd';
import { useCourses } from '../../hooks/useCourses';

const useStyles = createStyles(theme => ({
  navbar: {
    backgroundColor: theme.white,
    border: '1px solid #e1e2e3;',
    borderRadius: '5px',
  },

  header: {
    paddingBottom: theme.spacing.md,
    marginBottom: `calc(${theme.spacing.md} * 1.5)`,
    borderBottom: `1px solid #e1e2e3;`,
  },

  link: {
    ...theme.fn.focusStyles(),
    display: 'flex',
    alignItems: 'center',
    textDecoration: 'none',
    fontSize: theme.fontSizes.sm,
    color: theme.black,
    padding: `${theme.spacing.xs} ${theme.spacing.sm}`,
    borderRadius: theme.radius.sm,
    fontWeight: 500,
  },

  linkIcon: {
    ref: getStylesRef('icon'),
    color: theme.black,
    opacity: 0.75,
    marginRight: theme.spacing.sm,
  },

  linkActive: {
    '&, &:hover': {
      backgroundColor: theme.colors.gray[1],
      [`& .${getStylesRef('icon')}`]: {
        opacity: 0.9,
      },
    },
  },

  body: {
    backgroundColor: theme.white,
    border: '1px solid #e1e2e3;',
    borderRadius: '5px',
    minHeight: '800px',
    width: '100%',
    marginLeft: '1rem',
    padding: '1rem',
    marginBottom: '3rem',
  },
}));

const data = [
  { link: '', label: '부트캠프', icon: IconRocket },
  { link: '', label: '커뮤니티', icon: IconNews },
];

const BookmarkCoursePage = () => {
  const { classes, cx } = useStyles();
  const [active] = useState('부트캠프');

  const links = data.map(item => (
    <a
      className={cx(classes.link, { [classes.linkActive]: item.label === active })}
      href={item.link}
      key={item.label}
      onClick={event => {
        event.preventDefault();
      }}
    >
      <item.icon className={classes.linkIcon} stroke={1.5} />
      <span>{item.label}</span>
    </a>
  ));

  const { fetchCourses, size } = useCourses();
  const [currentPage, setCurrentPage] = useState(1);

  const handlePageChange = (page: React.SetStateAction<number>) => {
    setCurrentPage(page);
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  useEffect(() => {
    fetchCourses('popular', currentPage).catch();
  }, [currentPage]);

  useEffect(() => {
    fetchCourses('popular', currentPage).catch();
  }, []);

  return (
    <Wrapper>
      {/*<BookmarkLayout>*/}
      {/*  <Flex justify="center" align="flex-start" direction="row">*/}
      {/*    <MediaQuery query="(max-width: 75em)" styles={{ display: 'none' }}>*/}
      {/*      <Navbar height={230} width={{ sm: 250 }} p="md" className={classes.navbar}>*/}
      {/*        <Navbar.Section grow>*/}
      {/*          <Group className={classes.header} position="apart">*/}
      {/*            <Text fz={20} ml={16} fw={700}>*/}
      {/*              내 북마크*/}
      {/*            </Text>*/}
      {/*          </Group>*/}
      {/*          {links}*/}
      {/*        </Navbar.Section>*/}
      {/*      </Navbar>*/}
      {/*    </MediaQuery>*/}
      {/*    <Container className={classes.body}>*/}
      {/*      <Text fz={20} fw={700} pl={16} className={classes.header}>*/}
      {/*        북마크 코스*/}
      {/*      </Text>*/}
      {/*      <Container p={0} style={{ minHeight: '600px' }}>*/}
      {/*        {*/}
      {/*          bookmarkedCount === 0 ? (*/}
      {/*            <Flex justify={'center'} mt={48}>*/}
      {/*              <Text fz="xl" fw={600} color="gray.5">*/}
      {/*                아직 북마크 저장한 코스가 없습니다...*/}
      {/*              </Text>*/}
      {/*            </Flex>*/}
      {/*          ) : null*/}
      {/*          // <CourseCardList courses={currentCourses} displayBookmarked />*/}
      {/*        }*/}
      {/*      </Container>*/}
      {/*      <PaginationWrapper>*/}
      {/*        <Pagination*/}
      {/*          current={currentPage}*/}
      {/*          pageSize={size}*/}
      {/*          total={bookmarkedCount}*/}
      {/*          onChange={handlePageChange}*/}
      {/*          showSizeChanger={false}*/}
      {/*        />*/}
      {/*      </PaginationWrapper>*/}
      {/*    </Container>*/}
      {/*  </Flex>*/}
      {/*</BookmarkLayout>*/}
    </Wrapper>
  );
};

export default BookmarkCoursePage;
