import { Card, Title, Avatar, TextInput, Button, Group, Text } from '@mantine/core';
import { AvatarWrapper, IconWrapper, ProfileImage, ProfileLayout, SaveButton, TitleWrapper, Wrapper } from './style';
import { CheckIconTabler, EditSaveIcon, ImageUploadIcon } from '../../constants/icons';

import React, { useEffect, useRef } from 'react';
import { hasLength, useForm } from '@mantine/form';
import StackSelect from './StackSelect';
import { useProfile } from '../../hooks/useProfile';
import useImage from '../../hooks/useImage';

const ProfilePage = () => {
  const {
    loadingProfile,
    fetchProfile,
    profileImage,
    email,
    nickname,
    job,
    stacks,
    isNicknameDuplicate,
    nicknameCheckDone,
    fetchNicknameDuplicate,
    updateProfileImage,
    handleSubmit,
    handleNicknameChange,
  } = useProfile();
  const { uploadProfileImage } = useImage();

  const form = useForm({
    initialValues: { email: email, nickname: nickname, job: job, stacks: stacks },
    validateInputOnChange: true,
    validate: {
      nickname: hasLength({ min: 2, max: 12 }, '2~12자의 닉네임을 입력해주세요.'),
      job: value => (value ? hasLength({ min: 2, max: 12 }, '2~12자의 직업을 입력해주세요.')(value) : null),
    },
  });

  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleAvatarClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.item(0);
    if (file) {
      uploadProfileImage(file)
        .then(imageUrl => updateProfileImage(imageUrl))
        .then(() => fetchProfile());
    }
  };

  useEffect(() => {
    form.setValues({ email, nickname, job, stacks });
  }, [stacks]);

  useEffect(() => {
    fetchProfile().catch();
  }, []);

  return (
    <Wrapper>
      <input
        ref={fileInputRef}
        type="file"
        hidden
        accept="image/jpeg,image/pjpeg,image/png,image/bmp,image/tiff"
        onChange={handleFileChange}
      />
      <ProfileLayout>
        <Card withBorder padding="xl" radius="md">
          <TitleWrapper>
            <Title order={2}>프로필 관리</Title>
          </TitleWrapper>
          <ProfileImage>
            <AvatarWrapper>
              <Avatar
                src={profileImage}
                size={80}
                radius={80}
                style={{ cursor: 'pointer', zIndex: '100' }}
                onClick={handleAvatarClick}
              />
              <IconWrapper>
                <ImageUploadIcon />
              </IconWrapper>
            </AvatarWrapper>
          </ProfileImage>
          <form
            onSubmit={form.onSubmit(values => {
              handleSubmit(values);
            })}
          >
            <TextInput
              {...form.getInputProps('email')}
              withAsterisk
              disabled
              mt="xl"
              label="이메일"
              placeholder="이메일을 입력하세요"
              inputWrapperOrder={['label', 'input', 'description', 'error']}
              style={{ cursor: 'not-allowed' }}
            />
            <TextInput
              {...form.getInputProps('nickname')}
              withAsterisk
              mt="xl"
              label="닉네임"
              placeholder="닉네임을 입력하세요"
              onChange={event => {
                form.getInputProps('nickname').onChange(event);
                handleNicknameChange(event);
              }}
              inputWrapperOrder={['label', 'input', 'description', 'error']}
            />
            <Group position="right" spacing="xl">
              {isNicknameDuplicate && nicknameCheckDone && (
                <Text fz="sm" color="red.7">
                  이미 사용 중인 닉네임입니다.
                </Text>
              )}
              {!isNicknameDuplicate && nicknameCheckDone && (
                <Text fz="sm" color="blue.7">
                  사용 가능한 닉네임입니다.
                </Text>
              )}
              <Button
                onClick={fetchNicknameDuplicate}
                leftIcon={<CheckIconTabler />}
                color="gray.1"
                radius="md"
                mt={8}
                style={{ color: '#495057', fontSize: '14px', fontWeight: '400', padding: '0.5rem' }}
              >
                중복 확인
              </Button>
            </Group>
            <TextInput
              {...form.getInputProps('job')}
              label="직업"
              mt="8"
              placeholder="직업을 입력하세요"
              inputWrapperOrder={['label', 'input', 'description', 'error']}
            />
            {!loadingProfile && <StackSelect />}
            <SaveButton>
              <Button
                type="submit"
                leftIcon={<EditSaveIcon />}
                radius="md"
                mt={16}
                disabled={isNicknameDuplicate}
                color="blue.6"
                style={{ fontSize: '14px', fontWeight: '500', padding: '0.5rem' }}
              >
                저장하기
              </Button>
            </SaveButton>
          </form>
        </Card>
      </ProfileLayout>
    </Wrapper>
  );
};

export default ProfilePage;
