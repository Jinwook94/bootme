import React, { useState } from 'react';
import { Modal, Popover } from 'antd';
import { ThreeDotsIcon1 } from '../../../constants/icons';
import { IconWrapper, Item, Items, LinkItem, MobileThreeDots, ThreeDotsWrapper } from './style';
import { MobileButtonWrapper } from '../style';
import { DeleteOutlined } from '@ant-design/icons';
import { IconEdit } from '@tabler/icons-react';
import { usePost } from '../../../hooks/usePost';

export const PostThreeDotsDropdown = ({ handleEditClick, postId, isMobile }: PostThreeDotsDropdownProps) => {
  const { deletePost } = usePost();
  const [isDropdown, setIsDropdown] = useState(false);
  const [isDeleteModal, setIsDeleteModal] = useState(false);

  const handleDropdownVisible = () => {
    setIsDropdown(!isDropdown);
  };

  const showModal = () => {
    setIsDeleteModal(true);
  };

  const handleOk = () => {
    deletePost(postId);
    setIsDeleteModal(false);
  };

  const handleCancel = () => {
    setIsDeleteModal(false);
  };

  const threeDotsDropdown = () => {
    return (
      <>
        <Items>
          <Item
            onClick={() => {
              handleDropdownVisible();
              handleEditClick();
            }}
          >
            <LinkItem>
              <IconWrapper>
                <IconEdit />
              </IconWrapper>
              게시글 수정
            </LinkItem>
          </Item>
          <Item
            onClick={() => {
              showModal();
              handleDropdownVisible();
            }}
          >
            <LinkItem>
              <IconWrapper>
                <DeleteOutlined />
              </IconWrapper>
              게시글 삭제
            </LinkItem>
          </Item>
        </Items>
      </>
    );
  };

  return (
    <>
      <Popover
        content={threeDotsDropdown}
        trigger="click"
        placement="bottomRight"
        open={isDropdown}
        onOpenChange={handleDropdownVisible}
      >
        {isMobile ? (
          <MobileButtonWrapper>
            <MobileThreeDots>
              <ThreeDotsIcon1 />
            </MobileThreeDots>
          </MobileButtonWrapper>
        ) : (
          <ThreeDotsWrapper>
            <ThreeDotsIcon1 />
          </ThreeDotsWrapper>
        )}
      </Popover>
      <Modal title="게시글 삭제" open={isDeleteModal} onOk={handleOk} onCancel={handleCancel}>
        <p>게시글을 삭제하시겠습니까?</p>
      </Modal>
    </>
  );
};

interface PostThreeDotsDropdownProps {
  handleEditClick: () => void;
  postId: number;
  isMobile: boolean;
}
