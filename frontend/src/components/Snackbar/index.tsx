import { Notification } from '@mantine/core';
import { IconCheck, IconExclamationMark } from '@tabler/icons-react';
import React from 'react';
import { Wrapper } from './style';

interface SnackbarProps {
  message: string;
  displayIcon: string;
}

const Snackbar = ({ message, displayIcon }: SnackbarProps) => {
  return (
    <Wrapper>
      {displayIcon === 'check' ? (
        <Notification icon={<IconCheck size="1.1rem" />} color="teal" title={message} withCloseButton={false} />
      ) : displayIcon === 'exclamation' ? (
        <Notification
          icon={<IconExclamationMark size="1.1rem" />}
          color="teal"
          title={message}
          withCloseButton={false}
        />
      ) : null}
    </Wrapper>
  );
};

export default Snackbar;
