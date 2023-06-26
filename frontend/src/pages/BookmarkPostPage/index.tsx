import React, { useEffect, useState } from 'react';
import { Navbar, Group, Text, Flex, Container, MediaQuery } from '@mantine/core';
import { BookmarkLayout, NavBarItem, PostCardList, Wrapper } from './style';
import { PaginationWrapper } from '../CourseListPage/style';
import { Pagination } from 'antd';
import { useBookmarks } from '../../hooks/useBookmarks';
import { BOOKMARK_TYPE } from '../../constants/others';
import { Post } from '../../types/post';
import PostCard from '../PostListPage/PostCard';
import { useNavigate } from 'react-router-dom';
import PATH from '../../constants/path';
import { PapersIcon, RocketIcon } from '../../constants/icons';
import LoadingSpinner from '../../components/@common/LoadingSpinner';
import { useStyles } from '../BookmarkCoursePage/style';

const BookmarkPostPage = () => {
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
    fetchBookmarkedItems(BOOKMARK_TYPE.POST, currentPage, size).then(() => setIsLoading(false));
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
                <NavBarItem onClick={() => navigate(PATH.BOOKMARK.COURSE)}>
                  <RocketIcon />
                  <span>부트캠프</span>
                </NavBarItem>
                <NavBarItem style={{ backgroundColor: '#f1f3f5', borderRadius: '0.25rem' }}>
                  <PapersIcon />
                  <span>커뮤니티</span>
                </NavBarItem>
              </Navbar.Section>
            </Navbar>
          </MediaQuery>
          <Container className={classes.body}>
            <Text fz={20} fw={700} pt={16} pl={16} className={classes.bodyHeaderDesktop}>
              북마크 게시글
            </Text>
            <Flex direction={'row'} justify={'space-around'} className={classes.bodyHeaderMobile}>
              <Text
                fz={20}
                fw={700}
                py={16}
                style={{ flex: 1, textAlign: 'center', borderBottom: '2px solid rgb(242, 242, 242)' }}
                className={classes.mobileText}
                onClick={() => navigate(PATH.BOOKMARK.COURSE)}
              >
                코스
              </Text>
              <Text
                fz={20}
                fw={700}
                py={16}
                style={{ flex: 1, textAlign: 'center', borderBottom: '2px solid rgb(0, 132, 255)' }}
                className={classes.mobileText}
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
                    아직 북마크 저장한 게시글이 없습니다...
                  </Text>
                </Flex>
              ) : (
                <PostCardList>
                  {currentItems?.map((post: Post) => (
                    <PostCard
                      key={post.id}
                      id={post.id}
                      writerId={post.writerId}
                      writerNickname={post.writerNickname}
                      writerProfileImage={post.writerProfileImage}
                      topic={post.topic}
                      title={post.title}
                      contentExcerpt={post.contentExcerpt}
                      likes={post.likes}
                      createdAt={post.createdAt}
                      commentCount={post.commentCount}
                      voted={post.voted}
                      bookmarked={post.bookmarked}
                      viewed={post.viewed}
                    />
                  ))}
                </PostCardList>
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

export default BookmarkPostPage;
