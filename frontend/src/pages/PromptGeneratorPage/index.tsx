import {
  BackgroundWrapper,
  Body,
  Buttons,
  Header,
  InputWrapper,
  PageHeader,
  PromptGeneratorButton,
  PromptLayout,
  PromptWrapper,
  PromptSubmissionButton,
  Wrapper,
} from './style';
import { Title, Text, Button, Textarea, Flex, Center } from '@mantine/core';
import React, { useEffect, useRef, useState } from 'react';
import './style.css';
import { usePrompt } from '../../hooks/usePrompt';
import ApiDesignPromptInputs from './Inputs/ApiDesignPromptInputs';
import CategoryInput from './InputModules/CategoryInput';
import GeneralPromptInputs from './Inputs/GeneralPromptInputs';
import FeatureImplementPromptInputs from './Inputs/FeatureImplementPromptInputs';
import Chat from './Chat';
import LoadingSpinner from '../../components/@common/LoadingSpinner';
import { modals } from '@mantine/modals';

const PromptGeneratorPage = () => {
  const {
    isGenerating,
    isPromptGenerated,
    isSubmitting,
    isMessaging,
    category,
    prompt,
    setPrompt,
    submitGeneralPrompt,
    submitFeaturePrompt,
    submitApiDesignPrompt,
    sendPromptSubmissionRequest,
  } = usePrompt();
  const [showChat, setShowChat] = useState(false);
  const chatRef = useRef<HTMLDivElement | null>(null);

  const handleGenerateClick = () => {
    switch (category) {
      case 'general':
        submitGeneralPrompt();
        break;
      case 'feature':
        submitFeaturePrompt();
        break;
      case 'apiDesign':
        submitApiDesignPrompt();
        break;
      default:
        console.log('Invalid category');
    }
  };

  const handleSubmissionClick = async () => {
    if (showChat) {
      modals.openConfirmModal({
        title: '프로필 사진 변경',
        children: (
          <Center>
            <Text size="sm">다시 프롬프트를 입력하면 대화 기록이 초기화 됩니다.</Text>
          </Center>
        ),
        labels: { confirm: '네', cancel: '아니오' },
        onCancel: () => {},
        onConfirm: async () => {
          setShowChat(false);
          await sendPromptSubmissionRequest();
          setShowChat(true);
        },
      });
    } else {
      setShowChat(true);
      await sendPromptSubmissionRequest();
    }
  };

  useEffect(() => {
    if (showChat && chatRef.current !== null) {
      chatRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [showChat]);

  return (
    <BackgroundWrapper>
      <PromptLayout>
        <PageHeader>
          <Title fz={28} fw={600}>
            ChatGPT 프롬프트 생성기
          </Title>
          <Text mt={12} fz={18} fw={400} lh="24px">
            최적화된 프롬프트로 정확한 답변을 얻으세요.
          </Text>
          <Text mt={4} fz={18} fw={400} lh="24px">
            프롬프트를 저장하여 쉽게 재사용할 수 있습니다.
          </Text>
        </PageHeader>
        <Wrapper>
          <InputWrapper>
            <Header>입력</Header>
            <Body>
              <CategoryInput />
              {category === 'general' && <GeneralPromptInputs />}
              {category === 'feature' && <FeatureImplementPromptInputs />}
              {category === 'apiDesign' && <ApiDesignPromptInputs />}
            </Body>
          </InputWrapper>
          <PromptWrapper>
            <Header>프롬프트</Header>
            <Body>
              {isGenerating ? (
                <Flex justify={'center'} mt={96}>
                  <LoadingSpinner fixed={false} />
                </Flex>
              ) : isPromptGenerated ? (
                <Textarea
                  className={'prompt-text-area-input'}
                  aria-label="prompt"
                  placeholder="프롬프트"
                  minRows={27}
                  radius="sm"
                  style={{ height: '100%' }}
                  value={prompt}
                  onChange={event => setPrompt(event.currentTarget.value)}
                />
              ) : (
                <Flex justify="center" align="center">
                  <Text color="gray.4" mt={32} fz="lg">
                    프롬프트를 생성하세요
                  </Text>
                </Flex>
              )}
            </Body>
          </PromptWrapper>
        </Wrapper>
        <Buttons>
          <PromptGeneratorButton>
            <Button mr={4} onClick={handleGenerateClick} disabled={isGenerating || isSubmitting || isMessaging}>
              프롬프트 생성
            </Button>
          </PromptGeneratorButton>
          <PromptSubmissionButton>
            <div
              style={{
                cursor: isPromptGenerated ? 'pointer' : 'not-allowed',
              }}
            >
              <Button
                mr={4}
                onClick={isPromptGenerated ? handleSubmissionClick : undefined}
                disabled={!isPromptGenerated || isGenerating || isSubmitting || isMessaging}
              >
                프롬프트 입력
              </Button>
            </div>
          </PromptSubmissionButton>
        </Buttons>
        {showChat && (
          <div ref={chatRef}>
            <Chat />
          </div>
        )}
      </PromptLayout>
    </BackgroundWrapper>
  );
};

export default PromptGeneratorPage;
