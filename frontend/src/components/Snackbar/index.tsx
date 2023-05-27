import { Notification } from '@mantine/core';
import { IconCheck } from '@tabler/icons-react';
import React from 'react';
import { Wrapper } from './style';
import { useSnackbar } from '../../hooks/useSnackbar';

interface SnackbarProps {
  message: string;
}

const Snackbar = ({ message }: SnackbarProps) => {
  const { hideSnackbar } = useSnackbar();
  return (
    <>
      <Wrapper>
        <Notification icon={<IconCheck size="1.1rem" />} color="teal" title={message} onClose={hideSnackbar} />
      </Wrapper>
    </>
  );
};

export default Snackbar;
