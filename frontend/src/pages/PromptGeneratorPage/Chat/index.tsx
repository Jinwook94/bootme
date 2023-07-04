import { usePrompt } from '../../../hooks/usePrompt';
import { Conversation, Header, IconWrapper, MessageWrapper, ProfileImageWrapper, UserInput, Wrapper } from './style';
import { Avatar, Flex, Text, Textarea } from '@mantine/core';
import { useProfile } from '../../../hooks/useProfile';
import { useEffect, useRef, useState } from 'react';
import { SendMessageIcon, SparklesIcon2 } from '../../../constants/icons';
import './style.css';
import { useLogin } from '../../../hooks/useLogin';
import LoadingSpinner from '../../../components/@common/LoadingSpinner';

const Chat = () => {
  const { isLogin } = useLogin();
  const { prompt, gptResponse, sendMessage } = usePrompt();
  const { fetchProfile, profileImage } = useProfile();
  const [initialMessage, setInitialMessage] = useState<string>('');
  const [initialResponse, setInitialResponse] = useState<string>('');
  const [message, setMessage] = useState<string>('');
  const [messages, setMessages] = useState<Array<{ message: string; response: string | null }>>([]);
  const memberId = localStorage.getItem('memberId') || '';
  const [isComposing, setIsComposing] = useState(false);
  const userInputRef = useRef<HTMLDivElement | null>(null);

  const handleSend = async () => {
    if (!message.trim() || isComposing) return;

    setMessages([...messages, { message, response: null }]);
    setMessage('');

    const response = await sendMessage(message);
    setMessages(prevMessages =>
      prevMessages.map((msg, i) => (i === prevMessages.length - 1 ? { ...msg, response } : msg))
    );
  };

  useEffect(() => {
    if (isLogin) {
      fetchProfile(memberId).catch();
    }
  }, [isLogin, memberId]);

  useEffect(() => {
    if (!initialResponse && gptResponse) {
      setInitialResponse(gptResponse);
    }
  }, [gptResponse]);

  useEffect(() => {
    setInitialMessage(prompt);
  }, [prompt]);

  useEffect(() => {
    if (userInputRef.current) {
      userInputRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages, gptResponse]);

  const GptAnswer = ({ response }: { response: string | null; marginTop?: number }) => (
    <Flex
      direction={'row'}
      p={24}
      pb={28}
      style={{
        backgroundColor: 'rgb(247, 247, 248)',
        borderTop: '1px solid rgba(0,0,0,0.1)',
        borderBottom: '1px solid rgba(0,0,0,0.1)',
      }}
    >
      <ProfileImageWrapper>
        <Avatar
          src={'https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/company/openai.png'}
          size={30}
          mr="1.5rem"
        />
      </ProfileImageWrapper>
      <MessageWrapper>
        {response ? (
          <Text color={'rgb(55, 65, 81)'}>{response}</Text>
        ) : (
          <Flex justify={'center'}>
            <LoadingSpinner size={'default'} fixed={false} />
          </Flex>
        )}
      </MessageWrapper>
    </Flex>
  );
  const UserMessage = ({ message }: { message: string }) => (
    <Flex direction={'row'} p={24} pb={28}>
      <ProfileImageWrapper className="chat-avatar">
        <Avatar src={isLogin ? profileImage : null} size={30} radius={80} mr="1.5rem" />
      </ProfileImageWrapper>
      <MessageWrapper>
        <Text color={'rgb(52, 53, 65)'}>{message}</Text>
      </MessageWrapper>
    </Flex>
  );

  return (
    <Wrapper>
      <Header>
        <IconWrapper>
          <SparklesIcon2 />
        </IconWrapper>
        <Text fz={14}>Model: GPT-3.5</Text>
      </Header>
      <Conversation>
        <UserMessage message={initialMessage} />
        <GptAnswer response={initialResponse} />
        {messages.map((msg, index) => (
          <div key={index}>
            <UserMessage message={msg.message} />
            <GptAnswer response={msg.response} />
          </div>
        ))}
      </Conversation>
      <UserInput ref={userInputRef}>
        <Textarea
          className={'chat-user-input'}
          aria-label="userMessage"
          placeholder="메세지를 입력하세요"
          autosize
          minRows={1}
          maxRows={4}
          style={{ width: '100%', border: 'none' }}
          onCompositionStart={() => setIsComposing(true)}
          onCompositionEnd={() => setIsComposing(false)}
          value={message}
          onChange={event => setMessage(event.currentTarget.value)}
          onKeyDown={event => {
            if (event.key === 'Enter' && !event.shiftKey) {
              event.preventDefault();
              handleSend().catch();
            }
          }}
        />
        <IconWrapper onClick={handleSend}>
          <SendMessageIcon />
        </IconWrapper>
      </UserInput>
    </Wrapper>
  );
};

export default Chat;
