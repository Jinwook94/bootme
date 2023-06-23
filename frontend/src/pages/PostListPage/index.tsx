import {
  BodyWrapper,
  CommunityPageLayout,
  PostCardList,
  CreatePostInput,
  CreatePostWrapper,
  ProfilePic,
  ProfileWrapper1,
  ProfileWrapper2,
  ProfileWrapper3,
  ProfileWrapper4,
  ProfileWrapper5,
  SearchWrapper,
  SortName,
  SortOption,
  TopicFilterButton,
  SortAndFilterWrapper,
  SortWrapper,
  StyledSearch,
  SortSearchDesktop,
  SortAndFilterMobile,
  MobileHeader,
  MobileHeaderTextMedium,
  MobileHeaderTextLarge,
  NoResultMessage,
  SortButtons,
  HotButton,
  NewestButton,
} from './style';
import { Space } from 'antd';
import { POST_FILTERS } from '../../constants/filters';
import PATH from '../../constants/path';
import { FireIcon2, FireIconBlue, SparklesIcon, SparklesIconBlue } from '../../constants/icons';
import { Post } from '../../types/post';
import 'react-quill/dist/quill.snow.css';
import 'react-quill/dist/quill.core.css';
import React, { useEffect, useRef, useState } from 'react';
import { Link, useNavigate, useNavigationType, useSearchParams } from 'react-router-dom';
import { usePost } from '../../hooks/usePost';
import { usePostFilters } from '../../hooks/useFilters';
import PostCard from './PostCard';
import TopicDropdown from './TopicDropdown';
import BottomTapBar from './BottomTabBar';
import SideTab from './SideTab';
import { useSnackbar } from '../../hooks/useSnackbar';
import { useLogin } from '../../hooks/useLogin';
import SNACKBAR_MESSAGE, { CHECK } from '../../constants/snackbar';
import { useInView } from 'react-intersection-observer';
import { Popover } from '@mantine/core';
import ProfileCard from '../../components/ProfileCard';
import LoadingSpinner from '../../components/@common/LoadingSpinner';

const PostListPage = () => {
  const navigationType = useNavigationType();
  const [searchParams] = useSearchParams();
  const { showSnackbar } = useSnackbar();
  const { isLogin } = useLogin();
  const navigate = useNavigate();
  const {
    sortOption,
    postList,
    postCount,
    page,
    isLastPosts,
    setPage,
    onSearch,
    fetchPostList,
    setSortOption,
    isLoadingPost,
  } = usePost();
  const { selectedFilters, clearAndAddFilter } = usePostFilters();
  const profilePicture = localStorage.getItem('profileImage') || '';
  const memberId = localStorage.getItem('memberId') || '';
  const [initialized, setInitialized] = useState(false);
  const { ref: pageEndRef, inView } = useInView({
    threshold: 0,
  });

  const handleCreatePost = () => {
    if (isLogin) {
      navigate(PATH.POST.WRITE);
    } else {
      showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, CHECK);
    }
  };

  useEffect(() => {
    if (inView && !isLastPosts && !isLoadingPost) {
      setPage(page + 1);
    }
  }, [inView, isLastPosts, page, isLoadingPost]);

  useEffect(() => {
    if (navigationType === 'POP') {
      const savedScrollPosition = sessionStorage.getItem('scrollPosition');
      if (savedScrollPosition) {
        window.scrollTo(0, Number(savedScrollPosition));
        sessionStorage.removeItem('scrollPosition');
      }
    }
  }, [navigationType]);

  useEffect(() => {
    const handleScroll = () => {
      const scrollPosition = window.scrollY || document.documentElement.scrollTop;
      sessionStorage.setItem('scrollPosition', String(scrollPosition));
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  useEffect(() => {
    const searchQuery = searchParams.get('search');
    if (searchQuery) {
      onSearch(searchQuery);
    }

    const topic = searchParams.get('topic');
    if (topic) {
      clearAndAddFilter(POST_FILTERS.TOPIC.filterName, topic);
    } else {
      clearAndAddFilter(POST_FILTERS.TOPIC.filterName, '');
    }

    const sort = searchParams.get('sort');
    if (sort) {
      setSortOption(sort);
    }

    const pageNum = searchParams.get('page');
    if (pageNum) {
      setPage(parseInt(pageNum, 10));
    } else {
      setPage(1);
    }

    setInitialized(true);
  }, [searchParams]);

  const prevSortOption = useRef(sortOption);
  const prevSelectedFilters = useRef(selectedFilters);
  const prevPage = useRef(page);
  const [isInitialized, setIsInitialized] = useState(false);

  useEffect(() => {
    if (
      sortOption !== prevSortOption.current ||
      selectedFilters !== prevSelectedFilters.current ||
      page !== prevPage.current
    ) {
      fetchPostList(sortOption, page).catch();
    }

    if (
      isInitialized &&
      navigationType !== 'POP' &&
      (sortOption !== prevSortOption.current || selectedFilters !== prevSelectedFilters.current)
    ) {
      window.scrollTo({
        top: 0,
        behavior: 'smooth',
      });
    }

    prevSortOption.current = sortOption;
    prevSelectedFilters.current = selectedFilters;
    prevPage.current = page;
  }, [selectedFilters, page, initialized, sortOption, isInitialized]);

  useEffect(() => {
    setIsInitialized(true);
  }, []);

  return (
    <>
      <CommunityPageLayout>
        <BodyWrapper>
          <MobileHeader onClick={() => window.location.reload()}>
            <MobileHeaderTextLarge>커뮤니티</MobileHeaderTextLarge>
            <MobileHeaderTextMedium>아이디어를 공유하고 궁금증을 해결하세요.</MobileHeaderTextMedium>
          </MobileHeader>
          <CreatePostWrapper>
            <Popover position="bottom" withArrow shadow="md">
              <Popover.Target>
                <ProfileWrapper1>
                  <ProfileWrapper2>
                    <ProfileWrapper3>
                      <ProfileWrapper4></ProfileWrapper4>
                      <ProfileWrapper5>
                        {profilePicture && profilePicture !== 'null' && profilePicture !== 'undefined' && (
                          <ProfilePic src={profilePicture} />
                        )}
                        {isLogin && (
                          <Popover.Dropdown p={0}>
                            <ProfileCard memberId={memberId} />
                          </Popover.Dropdown>
                        )}
                      </ProfileWrapper5>
                    </ProfileWrapper3>
                  </ProfileWrapper2>
                </ProfileWrapper1>
              </Popover.Target>
            </Popover>
            <CreatePostInput onClick={handleCreatePost} placeholder="글 작성하기" />
          </CreatePostWrapper>
          <SortAndFilterMobile>
            <SortAndFilterWrapper>
              <SortButtons>
                <Link to={`${PATH.POST.LIST}?sort=hottest&topic=${selectedFilters?.topic?.[0]}`}>
                  <HotButton
                    size={'large'}
                    style={{ color: sortOption === 'hottest' ? '#0079d3' : 'rgb(135, 138, 140)' }}
                  >
                    {sortOption === 'hottest' ? <FireIconBlue /> : <FireIcon2 />}
                    인기글
                  </HotButton>
                </Link>
                <Link to={`${PATH.POST.LIST}?sort=newest&topic=${selectedFilters?.topic?.[0]}`}>
                  <NewestButton
                    size={'large'}
                    style={{ color: sortOption === 'newest' ? '#0079d3' : 'rgb(135, 138, 140)' }}
                  >
                    {sortOption === 'newest' ? <SparklesIconBlue /> : <SparklesIcon />}
                    최신글
                  </NewestButton>
                </Link>
              </SortButtons>
              <TopicFilterButton>
                <TopicDropdown />
              </TopicFilterButton>
            </SortAndFilterWrapper>
          </SortAndFilterMobile>
          <SortSearchDesktop>
            <SortWrapper>
              <Link to={`${PATH.POST.LIST}?sort=hottest&topic=${selectedFilters?.topic?.[0]}`}>
                <SortOption>
                  {sortOption === 'hottest' ? <FireIconBlue /> : <FireIcon2 />}
                  <SortName style={{ color: sortOption === 'hottest' ? '#0079d3' : 'rgb(135, 138, 140)' }}>
                    인기글
                  </SortName>
                </SortOption>
              </Link>
              <Link to={`${PATH.POST.LIST}?sort=newest&topic=${selectedFilters?.topic?.[0]}`}>
                <SortOption>
                  {sortOption === 'newest' ? <SparklesIconBlue /> : <SparklesIcon />}
                  <SortName style={{ color: sortOption === 'newest' ? '#0079d3' : 'rgb(135, 138, 140)' }}>
                    최신글
                  </SortName>
                </SortOption>
              </Link>
            </SortWrapper>
            <SearchWrapper>
              <Space direction="vertical">
                <StyledSearch placeholder="게시글 검색" onSearch={onSearch} size="large" style={{ width: 266 }} />
              </Space>
            </SearchWrapper>
          </SortSearchDesktop>
          {postCount === 0 ? (
            <NoResultMessage>조회된 게시글이 없습니다.</NoResultMessage>
          ) : (
            <PostCardList>
              {postList?.map((post: Post) => (
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
                />
              ))}
              {isLoadingPost ? <LoadingSpinner /> : <div ref={pageEndRef} />}
            </PostCardList>
          )}
        </BodyWrapper>
        <SideTab />
      </CommunityPageLayout>
      <BottomTapBar />
    </>
  );
};

export default PostListPage;
