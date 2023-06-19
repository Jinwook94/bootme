import { Popover } from 'antd';
import React, { useState } from 'react';

import { BookmarkIcon, LogoutIcon, ProfileIcon } from '../../../constants/icons';
import { useLogin } from '../../../hooks/useLogin';
import PATH from '../../../constants/path';
import { Link } from 'react-router-dom';
import {
  ContentWrapper,
  Figcaption,
  Figure,
  IconWrapper,
  Item,
  Items,
  LinkItem,
  LogoutButton,
  NickName,
  NickNameButton,
  Occupation,
  TextWrapper,
} from './style';

const UserDropDown = ({ nickName, profileImage, job }: UserDropDownProps) => {
  const content = () => {
    const { handleLogOut } = useLogin();
    return (
      <ContentWrapper>
        <Figure>
          <img
            width="42"
            height="42"
            src={profileImage ?? ''}
            alt="profile"
            style={{ borderRadius: '8px', objectFit: 'cover', marginRight: '15px' }}
          />
          <Figcaption>
            <NickName> {nickName} </NickName>
            <Occupation> {job} </Occupation>
          </Figcaption>
        </Figure>
        <Items>
          <Item>
            <Link to={PATH.PROFILE} onClick={hide}>
              <LinkItem>
                <IconWrapper>
                  <ProfileIcon />
                </IconWrapper>
                프로필 관리
              </LinkItem>
            </Link>
          </Item>
          <Item>
            <Link to={PATH.BOOKMARKS} onClick={hide}>
              <LinkItem>
                <IconWrapper>
                  <BookmarkIcon />
                </IconWrapper>
                북마크 코스
              </LinkItem>
            </Link>
          </Item>
        </Items>
        <LogoutButton onClick={handleLogOut}>
          <IconWrapper>
            <LogoutIcon />
          </IconWrapper>
          <TextWrapper>로그아웃</TextWrapper>
        </LogoutButton>
      </ContentWrapper>
    );
  };
  const [isOpen, setIsOpen] = useState<boolean>();
  const hide = () => {
    setIsOpen(false);
  };

  const handleOpenChange = (newOpen: boolean) => {
    setIsOpen(newOpen);
  };

  return (
    <Popover content={content} trigger="click" placement="bottomRight" open={isOpen} onOpenChange={handleOpenChange}>
      <>
        <NickNameButton>{nickName}님</NickNameButton>
      </>
    </Popover>
  );
};

export default UserDropDown;

interface UserDropDownProps {
  profileImage: string | null;
  nickName: string | null;
  job: string | null;
}
