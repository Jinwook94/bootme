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
import { useSearchParams } from 'react-router-dom';
import { useNavigation } from '../../hooks/useNavigation';
import { usePost } from '../../hooks/usePost';
import { usePostFilters } from '../../hooks/useFilters';
import PostCard from './PostCard';
import TopicDropdown from './TopicDropdown';
import BottomTapBar from './BottomTabBar';
import SideTab from './SideTab';

const PostListPage = () => {
  const [searchParams] = useSearchParams();
  const { goToPage } = useNavigation();
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
  }, []);

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
          <MobileHeader onClick={() => goToPage(PATH.POST.LIST)}>
            <MobileHeaderTextLarge>커뮤니티</MobileHeaderTextLarge>
            <MobileHeaderTextMedium>아이디어를 공유하고 궁금증을 해결하세요.</MobileHeaderTextMedium>
          </MobileHeader>
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
            <CreatePostInput onClick={() => goToPage(PATH.POST.WRITE)} placeholder="글 작성하기" />
          </CreatePostWrapper>
          <SortAndFilterMobile>
            <SortAndFilterWrapper>
              <SortButtons>
                <HotButton
                  size={'large'}
                  onClick={() => goToPage(`${PATH.POST.LIST}?sort=views&topic=${currentTopic}`)}
                  style={{ color: sortOption === 'views' ? '#0079d3' : 'rgb(135, 138, 140)' }}
                >
                  {sortOption === 'views' ? <FireIconBlue /> : <FireIcon2 />}
                  인기글
                </HotButton>
                <NewestButton
                  size={'large'}
                  onClick={() => goToPage(`${PATH.POST.LIST}?sort=newest&topic=${currentTopic}`)}
                  style={{ color: sortOption === 'newest' ? '#0079d3' : 'rgb(135, 138, 140)' }}
                >
                  {sortOption === 'newest' ? <SparklesIconBlue /> : <SparklesIcon />}
                  최신글
                </NewestButton>
              </SortButtons>
              <TopicFilterButton>
                <TopicDropdown />
              </TopicFilterButton>
            </SortAndFilterWrapper>
          </SortAndFilterMobile>
          <SortSearchDesktop>
            <SortWrapper>
              <SortOption onClick={() => goToPage(`${PATH.POST.LIST}?sort=views&topic=${currentTopic}`)}>
                {sortOption === 'views' ? <FireIconBlue /> : <FireIcon2 />}
                <SortName style={{ color: sortOption === 'views' ? '#0079d3' : 'rgb(135, 138, 140)' }}>인기글</SortName>
              </SortOption>
              <SortOption onClick={() => goToPage(`${PATH.POST.LIST}?sort=newest&topic=${currentTopic}`)}>
                {sortOption === 'newest' ? <SparklesIconBlue /> : <SparklesIcon />}
                <SortName style={{ color: sortOption === 'newest' ? '#0079d3' : 'rgb(135, 138, 140)' }}>
                  최신글
                </SortName>
              </SortOption>
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
