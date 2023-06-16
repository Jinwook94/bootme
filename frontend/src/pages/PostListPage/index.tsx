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
import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { usePost } from '../../hooks/usePost';
import { usePostFilters } from '../../hooks/useFilters';
import PostCard from './PostCard';
import TopicDropdown from './TopicDropdown';
import BottomTapBar from './BottomTabBar';
import SideTab from './SideTab';
import { useSnackbar } from '../../hooks/useSnackbar';
import { useLogin } from '../../hooks/useLogin';
import SNACKBAR_MESSAGE, { CHECK } from '../../constants/snackbar';

const PostListPage = () => {
  const [searchParams] = useSearchParams();
  const { showSnackbar } = useSnackbar();
  const { isLogin } = useLogin();
  const navigate = useNavigate();
  const {
    sortOption,
    postList,
    postCount,
    isEndOfPosts,
    page,
    setPage,
    onSearch,
    fetchPostList,
    setSortOption,
    setPostList,
    setEndOfPosts,
  } = usePost();
  const { selectedFilters, clearAndAddFilter } = usePostFilters();
  const [currentTopic, setCurrentTopic] = useState('');
  const profilePicture = localStorage.getItem('profileImage') || '';
  const [initialized, setInitialized] = useState(false);

  const handleCreatePost = () => {
    if (isLogin) {
      navigate(PATH.POST.WRITE);
    } else {
      showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, CHECK);
    }
  };

  const resetPageState = async () => {
    setPostList([]);
    setPage(1);
    setEndOfPosts(false);
  };

  useEffect(() => {
    const handleScroll = () => {
      if (
        window.innerHeight + document.documentElement.scrollTop !== document.documentElement.offsetHeight ||
        isEndOfPosts
      )
        return;
      setPage(page + 1);
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, [isEndOfPosts, page]);

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
    }

    setInitialized(true);
  }, [searchParams]);

  useEffect(() => {
    if (initialized) {
      if (page === 1) {
        resetPageState().then(() => fetchPostList(sortOption, 1));
      } else {
        fetchPostList(sortOption, page);
      }
    }
  }, [selectedFilters, sortOption, page, initialized]);

  useEffect(() => {
    setCurrentTopic(selectedFilters?.topic ? selectedFilters?.topic[0] : '');
  }, [selectedFilters]);

  return (
    <>
      <CommunityPageLayout>
        <BodyWrapper>
          <Link to={PATH.POST.LIST}>
            <MobileHeader>
              <MobileHeaderTextLarge>커뮤니티</MobileHeaderTextLarge>
              <MobileHeaderTextMedium>아이디어를 공유하고 궁금증을 해결하세요.</MobileHeaderTextMedium>
            </MobileHeader>
          </Link>
          <CreatePostWrapper>
            <ProfileWrapper1>
              <ProfileWrapper2>
                <ProfileWrapper3>
                  <ProfileWrapper4></ProfileWrapper4>
                  <ProfileWrapper5>
                    <ProfilePic src={profilePicture} />
                  </ProfileWrapper5>
                </ProfileWrapper3>
              </ProfileWrapper2>
            </ProfileWrapper1>
            <CreatePostInput onClick={handleCreatePost} placeholder="글 작성하기" />
          </CreatePostWrapper>
          <SortAndFilterMobile>
            <SortAndFilterWrapper>
              <SortButtons>
                <Link to={`${PATH.POST.LIST}?sort=views&topic=${currentTopic}`}>
                  <HotButton
                    size={'large'}
                    style={{ color: sortOption === 'views' ? '#0079d3' : 'rgb(135, 138, 140)' }}
                  >
                    {sortOption === 'views' ? <FireIconBlue /> : <FireIcon2 />}
                    인기글
                  </HotButton>
                </Link>
                <Link to={`${PATH.POST.LIST}?sort=newest&topic=${currentTopic}`}>
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
              <Link to={`${PATH.POST.LIST}?sort=views&topic=${currentTopic}`}>
                <SortOption>
                  {sortOption === 'views' ? <FireIconBlue /> : <FireIcon2 />}
                  <SortName style={{ color: sortOption === 'views' ? '#0079d3' : 'rgb(135, 138, 140)' }}>
                    인기글
                  </SortName>
                </SortOption>
              </Link>
              <Link to={`${PATH.POST.LIST}?sort=newest&topic=${currentTopic}`}>
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
                />
              ))}
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
