import React from 'react';
import { StyledButton } from './style';
import { Dropdown, MenuProps } from 'antd';
import { DownOutlined, UserOutlined } from '@ant-design/icons';
import { 개발질문, 부트캠프질문, 자유 } from '../../../constants/filters';

const TopicDropdown = ({ topic, setTopic }: TopicDropdownProps) => {
  const handleTopicSelect: MenuProps['onClick'] = e => {
    setTopic(e.key);
  };

  const items: MenuProps['items'] = [
    {
      label: 자유,
      key: 자유,
      icon: <UserOutlined />,
    },
    {
      label: 부트캠프질문,
      key: 부트캠프질문,
      icon: <UserOutlined />,
    },
    {
      label: 개발질문,
      key: 개발질문,
      icon: <UserOutlined />,
    },
  ];

  const menuProps = {
    items,
    selectable: true,
    defaultSelectedKeys: [자유],
    onClick: handleTopicSelect,
  };

  return (
    <Dropdown menu={menuProps} trigger={['click']}>
      <StyledButton>
        <UserOutlined />
        {topic}
        <DownOutlined />
      </StyledButton>
    </Dropdown>
  );
};

export default TopicDropdown;

interface TopicDropdownProps {
  topic: string | undefined;
  setTopic: (topic: string) => void;
}
