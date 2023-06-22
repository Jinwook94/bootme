import { Avatar, Badge, Button, Card, Center, createStyles, Group, MantineProvider, rem, Text } from '@mantine/core';
import { Wrapper } from './style';
import { useSnackbar } from '../../hooks/useSnackbar';
import SNACKBAR_MESSAGE, { CHECK } from '../../constants/snackbar';
import React, { useEffect } from 'react';
import { LoadingState, useProfile } from '../../hooks/useProfile';
import LoadingSpinner from '../@common/LoadingSpinner';

const useStyles = createStyles(theme => ({
  card: {
    backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white,
  },
  avatar: {
    border: `${rem(2)} solid ${theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white}`,
  },
  group: {
    display: '-webkit-box',
    WebkitLineClamp: 2,
    WebkitBoxOrient: 'vertical',
    overflow: 'hidden',
  },
}));

const ProfileCard = ({ memberId }: ProfileCardProps) => {
  const { showSnackbar } = useSnackbar();
  const { classes } = useStyles();
  const { fetchProfile, loadingState, profileImage, nickname, job, stacks } = useProfile();

  useEffect(() => {
    fetchProfile(memberId).catch();
  }, []);

  return (
    <Wrapper>
      <MantineProvider inherit theme={{ defaultGradient: { from: 'blue', to: 'teal', deg: 20 } }}>
        <Card padding="sm" radius="md" className={classes.card}>
          <div
            style={{
              display: 'flex',
              flexDirection: 'row',
              alignItems: 'center',
              padding: '0.5rem 1rem 0.5rem 0.5rem',
              borderBottom: '1px solid #E9ECEF',
              marginBottom: '0.5rem',
            }}
          >
            <Avatar src={profileImage} size={40} radius={80} mr={14} />
            <div style={{ display: 'flex', flexDirection: 'column' }}>
              <Text ta="left" fz="md" fw={500}>
                {nickname}
              </Text>
              <Text ta="left" fz="sm" c="dimmed">
                {job && job !== 'null' && job !== '' && job !== 'undefined' ? job : '-'}
              </Text>
            </div>
          </div>
          {stacks.length === 0 ? (
            <Center>
              <Text fz="sm" color="gray.7">
                등록된 기술 스택이 없습니다.
              </Text>
            </Center>
          ) : (
            <Group position="center" spacing={5} className={classes.group}>
              {stacks.map((stack, index) => (
                <Badge
                  key={index}
                  size="md"
                  radius="xl"
                  leftSection={<Avatar radius={8} size={18} src={stack.icon} />}
                  px={8}
                  py={12}
                  style={{
                    color: 'rgb(68, 87, 108)',
                    backgroundColor: '#F8F9FA',
                    textTransform: 'none',
                    fontSize: '12px',
                    marginRight: '3px',
                    marginTop: '5px',
                  }}
                >
                  {stack.name}
                </Badge>
              ))}
            </Group>
          )}
          <Button
            variant={'outline'}
            fullWidth
            radius="md"
            mt="md"
            size="xs"
            style={{ fontSize: '14px', borderColor: '#CED4DA', color: '#373A40' }}
            onClick={(event: React.MouseEvent<HTMLButtonElement>) => {
              event.stopPropagation();
              showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, CHECK);
            }}
          >
            팔로우
          </Button>
        </Card>
      </MantineProvider>
      {loadingState !== LoadingState.None && <LoadingSpinner />}
    </Wrapper>
  );
};

export default ProfileCard;

interface ProfileCardProps {
  memberId: number | string;
}
