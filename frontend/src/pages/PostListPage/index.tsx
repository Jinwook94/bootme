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
    isEndOfPosts,
    page,
    setPage,
    onSearch,
    fetchPostList,
    setSortOption,
    setPostList,
    setEndOfPosts,
    isLoadingPost,
  } = usePost();
  const { selectedFilters, clearAndAddFilter } = usePostFilters();
  const [currentTopic, setCurrentTopic] = useState('');
  const profilePicture = localStorage.getItem('profileImage') || '';
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

  const resetPageState = async () => {
    setPostList([]);
    setPage(1);
    setEndOfPosts(false);
  };

  useEffect(() => {
    if (inView && !isEndOfPosts && !isLoadingPost) {
      setPage(page + 1);
    }
  }, [inView, isEndOfPosts, page, isLoadingPost]);

  // 'POP' 타입의 네비게이션 이벤트가 발생하면 (뒤로 가기를 클릭)
  // 세션스토리지에서 스크롤 위치를 가져와 해당 위치로 이동함
  // 스크롤 위치를 사용한 후에는 해당 데이터를 세션스토리지 에서 제거
  useEffect(() => {
    if (navigationType === 'POP') {
      const savedScrollPosition = sessionStorage.getItem('scrollPosition');
      if (savedScrollPosition) {
        window.scrollTo(0, Number(savedScrollPosition));
        sessionStorage.removeItem('scrollPosition');
      }
    }
  }, [navigationType]);

  // 사용자가 스크롤할 때마다 세션 스토리지에 스크롤 위치를 저장
  // 컴포넌트가 언마운트 될 때 스크롤 이벤트 리스너를 제거하여 메모리 누수 방지
  useEffect(() => {
    const handleScroll = () => {
      const scrollPosition = window.scrollY || document.documentElement.scrollTop;
      sessionStorage.setItem('scrollPosition', String(scrollPosition));
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  useEffect(() => {
    if ('scrollRestoration' in history) {
      history.scrollRestoration = 'manual';
    }
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

  useEffect(() => {
    // 정렬 옵션 변경 처리
    if (sortOption !== prevSortOption.current) {
      resetPageState().then(() => fetchPostList(sortOption, 1, false));
    }
    // 토픽 필터, 페이지 변경 처리
    else if (initialized && (selectedFilters !== prevSelectedFilters.current || page !== prevPage.current)) {
      if (page === 1) {
        resetPageState().then(() => fetchPostList(sortOption, 1, false));
      } else {
        fetchPostList(sortOption, page, true);
      }
    }

    // 토픽 필터 변경 처리
    setCurrentTopic(selectedFilters?.topic ? selectedFilters?.topic[0] : '');

    // 이전 상태 업데이트
    prevSortOption.current = sortOption;
    prevSelectedFilters.current = selectedFilters;
    prevPage.current = page;
  }, [selectedFilters, page, initialized, sortOption]);

  return (
    <>
      <CommunityPageLayout>
        <BodyWrapper>
          <MobileHeader onClick={() => window.location.reload()}>
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
              {isLoadingPost ? null : <div ref={pageEndRef} />}
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
