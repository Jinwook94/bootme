import { Popover } from 'antd';
import { KakaoLogo, ShareIcon, UrlShareIcon } from '../../../constants/icons';
import React, { useEffect, useState } from 'react';
import { BOOTME_URL } from '../../../constants/others';
import { IconWrapper, Item, Items, LinkItem, ShareButtonWrapper } from './style';
import { useSecret } from '../../../hooks/useSecret';
import { useSnackbar } from '../../../hooks/useSnackbar';
import SNACKBAR_MESSAGE, { CHECK } from '../../../constants/snackbar';

const ShareButton = ({ course }: { course: Course | undefined }) => {
  const { showSnackbar } = useSnackbar();
  const [currentUrl, setCurrentUrl] = useState<string>();
  const { secrets } = useSecret();
  const KAKAO_KEY = secrets['kakao-javascript-key'];
  const [visible, setVisible] = useState(false);

  const handleVisibleChange = () => {
    setVisible(!visible);
  };

  const kakaoShare = () => {
    window.Kakao.Share.sendCustom({
      installTalk: true,
      templateId: 94331,
      templateArgs: {
        courseTitle: course?.title,
        companyLogo: course?.company.logoUrl,
        companyName: course?.company.name,
        shareUrl: `course/${course?.id}`,
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
    setCurrentUrl(`${BOOTME_URL}/course/${course?.id}`);
  }, [course]);

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
      <ShareButtonWrapper>
        <ShareIcon />
      </ShareButtonWrapper>
    </Popover>
  );
};

export default ShareButton;
