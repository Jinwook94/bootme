import { useSnackbar } from '../../../hooks/useSnackbar';
import React, { useEffect, useState } from 'react';
import { useSecret } from '../../../hooks/useSecret';
import SNACKBAR_MESSAGE, { CHECK } from '../../../constants/snackbar';
import { BOOTME_URL } from '../../../constants/others';
import { IconWrapper, Item, Items, LinkItem } from '../../CourseDetailPage/ShareButton/style';
import { KakaoLogo, ShareIcon3, UrlShareIcon } from '../../../constants/icons';
import { Popover } from 'antd';
import { PostDetail } from '../../../types/post';
import {
  ShareIconDesktop,
  ShareIconMobile,
  ShareIconTextDesktop,
  ShareIconTextMobile,
} from '../../PostListPage/PostCard/style';
import { ButtonIconWrapper, MobileButtonWrapper, MobileShareIcon } from '../style';

export const PostShareButtonInPostDetailPageDeskTop = ({ post }: { post: PostDetail | undefined }) => {
  const { showSnackbar } = useSnackbar();
  const [currentUrl, setCurrentUrl] = useState<string>();
  const { secrets } = useSecret();
  const KAKAO_KEY = secrets['kakao-javascript-key'];
  const [visible, setVisible] = useState(false);
  const [postImage, setPostImage] = useState<string>();

  const handleVisibleChange = () => {
    setVisible(!visible);
  };

  const kakaoShare = () => {
    window.Kakao.Share.sendCustom({
      installTalk: true,
      templateId: 94602,
      templateArgs: {
        postTitle: post?.title,
        postImage: postImage,
        shareUrl: `post/${post?.id}`,
      },
    });
  };

  const copyUrl = () => {
    if (currentUrl) {
      navigator.clipboard
        .writeText(currentUrl)
        .then(() => showSnackbar(SNACKBAR_MESSAGE.SUCCESS_COPY_URL_TO_CLIPBOARD, CHECK))
        .catch(e => {
          console.log('URL 복사 실패: ', e);
        });
    }
  };

  useEffect(() => {
    if (post?.content) {
      const domParser = new DOMParser();
      const contentDocument = domParser.parseFromString(post.content, 'text/html');
      const imgElement = contentDocument.querySelector('img');

      if (imgElement && imgElement.src) {
        setPostImage(imgElement.src);
      }
    }
  }, [post]);

  useEffect(() => {
    setCurrentUrl(`${BOOTME_URL}/post/${post?.id}`);
  }, [post]);

  useEffect(() => {
    if (KAKAO_KEY && !window.Kakao.isInitialized()) {
      window.Kakao.init(KAKAO_KEY);
    }
  }, [KAKAO_KEY]);

  const shareDropdown = () => {
    return (
      <>
        <Items>
          <Item onClick={handleVisibleChange}>
            <LinkItem onClick={copyUrl}>
              <IconWrapper>
                <UrlShareIcon />
              </IconWrapper>
              URL 복사
            </LinkItem>
          </Item>
          <Item onClick={handleVisibleChange}>
            <LinkItem onClick={kakaoShare}>
              <IconWrapper>
                <KakaoLogo />
              </IconWrapper>
              카카오톡
            </LinkItem>
          </Item>
        </Items>
      </>
    );
  };

  return (
    <Popover
      content={shareDropdown}
      trigger="click"
      placement="bottomRight"
      open={visible}
      onOpenChange={handleVisibleChange}
    >
      <ButtonIconWrapper>
        <ShareIcon3 />
      </ButtonIconWrapper>
    </Popover>
  );
};

export const PostShareButtonInPostDetailPageMobile = ({ post }: { post: PostDetail | undefined }) => {
  const { showSnackbar } = useSnackbar();
  const [currentUrl, setCurrentUrl] = useState<string>();
  const { secrets } = useSecret();
  const KAKAO_KEY = secrets['kakao-javascript-key'];
  const [visible, setVisible] = useState(false);
  const [postImage, setPostImage] = useState<string>();

  const handleVisibleChange = () => {
    setVisible(!visible);
  };

  const kakaoShare = () => {
    window.Kakao.Share.sendCustom({
      installTalk: true,
      templateId: 94602,
      templateArgs: {
        postTitle: post?.title,
        postImage: postImage,
        shareUrl: `post/${post?.id}`,
      },
    });
  };

  const copyUrl = () => {
    if (currentUrl) {
      navigator.clipboard
        .writeText(currentUrl)
        .then(() => showSnackbar(SNACKBAR_MESSAGE.SUCCESS_COPY_URL_TO_CLIPBOARD, CHECK))
        .catch(e => {
          console.log('URL 복사 실패: ', e);
        });
    }
  };

  useEffect(() => {
    if (post?.content) {
      const domParser = new DOMParser();
      const contentDocument = domParser.parseFromString(post.content, 'text/html');
      const imgElement = contentDocument.querySelector('img');

      if (imgElement && imgElement.src) {
        setPostImage(imgElement.src);
      }
    }
  }, [post]);

  useEffect(() => {
    setCurrentUrl(`${BOOTME_URL}/post/${post?.id}`);
  }, [post]);

  useEffect(() => {
    if (KAKAO_KEY && !window.Kakao.isInitialized()) {
      window.Kakao.init(KAKAO_KEY);
    }
  }, [KAKAO_KEY]);

  const shareDropdown = () => {
    return (
      <>
        <Items>
          <Item onClick={handleVisibleChange}>
            <LinkItem onClick={copyUrl}>
              <IconWrapper>
                <UrlShareIcon />
              </IconWrapper>
              URL 복사
            </LinkItem>
          </Item>
          <Item onClick={handleVisibleChange}>
            <LinkItem onClick={kakaoShare}>
              <IconWrapper>
                <KakaoLogo />
              </IconWrapper>
              카카오톡
            </LinkItem>
          </Item>
        </Items>
      </>
    );
  };

  return (
    <Popover
      content={shareDropdown}
      trigger="click"
      placement="bottomRight"
      open={visible}
      onOpenChange={handleVisibleChange}
    >
      <MobileButtonWrapper>
        <MobileShareIcon>
          <ShareIcon3 />
        </MobileShareIcon>
      </MobileButtonWrapper>
    </Popover>
  );
};

export const PostShareButtonInPostCardDesktop = ({
  postId,
  postTitle,
  postContent,
}: PostShareButtonInPostCardDesktopProps) => {
  const { showSnackbar } = useSnackbar();
  const [currentUrl, setCurrentUrl] = useState<string>();
  const { secrets } = useSecret();
  const KAKAO_KEY = secrets['kakao-javascript-key'];
  const [visible, setVisible] = useState(false);
  const [postImage, setPostImage] = useState<string>();

  const handleVisibleChange = (flag: boolean) => {
    setVisible(flag);
  };

  const stopPropagation = (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
  };

  const kakaoShare = (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
    setVisible(false);
    window.Kakao.Share.sendCustom({
      installTalk: true,
      templateId: 94602,
      templateArgs: {
        postTitle: postTitle,
        postImage: postImage,
        shareUrl: `post/${postId}`,
      },
    });
  };

  const copyUrl = (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
    setVisible(false);
    if (currentUrl) {
      navigator.clipboard
        .writeText(currentUrl)
        .then(() => showSnackbar(SNACKBAR_MESSAGE.SUCCESS_COPY_URL_TO_CLIPBOARD, CHECK))
        .catch(e => {
          console.log('URL 복사 실패: ', e);
        });
    }
  };

  useEffect(() => {
    if (postContent) {
      const domParser = new DOMParser();
      const contentDocument = domParser.parseFromString(postContent, 'text/html');
      const imgElement = contentDocument.querySelector('img');

      if (imgElement && imgElement.src) {
        setPostImage(imgElement.src);
      }
    }
  }, [postContent]);

  useEffect(() => {
    setCurrentUrl(`${BOOTME_URL}/post/${postId}`);
  }, []);

  useEffect(() => {
    if (KAKAO_KEY && !window.Kakao.isInitialized()) {
      window.Kakao.init(KAKAO_KEY);
    }
  }, [KAKAO_KEY]);

  const shareDropdown = () => {
    return (
      <>
        <Items>
          <Item>
            <LinkItem onClick={copyUrl}>
              <IconWrapper>
                <UrlShareIcon />
              </IconWrapper>
              URL 복사
            </LinkItem>
          </Item>
          <Item>
            <LinkItem onClick={kakaoShare}>
              <IconWrapper>
                <KakaoLogo />
              </IconWrapper>
              카카오톡
            </LinkItem>
          </Item>
        </Items>
      </>
    );
  };

  return (
    <>
      <Popover
        content={shareDropdown}
        trigger="click"
        placement="bottomRight"
        open={visible}
        onOpenChange={handleVisibleChange}
      >
        <ShareIconDesktop onClick={stopPropagation}>
          <ShareIcon3 />
          <ShareIconTextDesktop>공유</ShareIconTextDesktop>
        </ShareIconDesktop>
      </Popover>
    </>
  );
};

interface PostShareButtonInPostCardDesktopProps {
  postId: number;
  postTitle: string;
  postContent: string;
}

export const PostShareButtonInPostCardMobile = ({
  postId,
  postTitle,
  postContent,
}: PostShareButtonInPostCardMobileProps) => {
  const { showSnackbar } = useSnackbar();
  const [currentUrl, setCurrentUrl] = useState<string>();
  const { secrets } = useSecret();
  const KAKAO_KEY = secrets['kakao-javascript-key'];
  const [visible, setVisible] = useState(false);
  const [postImage, setPostImage] = useState<string>();

  const handleVisibleChange = (flag: boolean) => {
    setVisible(flag);
  };

  const stopPropagation = (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
  };

  const kakaoShare = (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
    setVisible(false);
    window.Kakao.Share.sendCustom({
      installTalk: true,
      templateId: 94602,
      templateArgs: {
        postTitle: postTitle,
        postImage: postImage,
        shareUrl: `post/${postId}`,
      },
    });
  };

  const copyUrl = (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
    setVisible(false);
    if (currentUrl) {
      navigator.clipboard
        .writeText(currentUrl)
        .then(() => showSnackbar(SNACKBAR_MESSAGE.SUCCESS_COPY_URL_TO_CLIPBOARD, CHECK))
        .catch(e => {
          console.log('URL 복사 실패: ', e);
        });
    }
  };

  useEffect(() => {
    if (postContent) {
      const domParser = new DOMParser();
      const contentDocument = domParser.parseFromString(postContent, 'text/html');
      const imgElement = contentDocument.querySelector('img');

      if (imgElement && imgElement.src) {
        setPostImage(imgElement.src);
      }
    }
  }, [postContent]);

  useEffect(() => {
    setCurrentUrl(`${BOOTME_URL}/post/${postId}`);
  }, []);

  useEffect(() => {
    if (KAKAO_KEY && !window.Kakao.isInitialized()) {
      window.Kakao.init(KAKAO_KEY);
    }
  }, [KAKAO_KEY]);

  const shareDropdown = () => {
    return (
      <>
        <Items>
          <Item>
            <LinkItem onClick={copyUrl}>
              <IconWrapper>
                <UrlShareIcon />
              </IconWrapper>
              URL 복사
            </LinkItem>
          </Item>
          <Item>
            <LinkItem onClick={kakaoShare}>
              <IconWrapper>
                <KakaoLogo />
              </IconWrapper>
              카카오톡
            </LinkItem>
          </Item>
        </Items>
      </>
    );
  };

  return (
    <>
      <Popover
        content={shareDropdown}
        trigger="click"
        placement="bottomRight"
        open={visible}
        onOpenChange={handleVisibleChange}
      >
        <ShareIconMobile onClick={stopPropagation}>
          <ShareIcon3 />
          <ShareIconTextMobile>공유</ShareIconTextMobile>
        </ShareIconMobile>
      </Popover>
    </>
  );
};

interface PostShareButtonInPostCardMobileProps {
  postId: number;
  postTitle: string;
  postContent: string;
}
