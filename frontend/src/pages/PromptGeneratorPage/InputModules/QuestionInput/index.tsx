import { usePrompt } from '../../../../hooks/usePrompt';
import { Textarea } from '@mantine/core';
import React from 'react';
import { Asterisk } from '../../style';

const QuestionInput = () => {
  const { question, setQuestion } = usePrompt();
  return (
    <div>
      <>
        <span>질문 내용</span>
        <Asterisk>*</Asterisk>
      </>
      <Textarea
        aria-label="question"
        placeholder="질문을 입력하세요."
        autosize
        minRows={2}
        maxRows={20}
        radius="sm"
        mt={6}
        value={question}
        onChange={e => setQuestion(e.currentTarget.value)}
      />
    </div>
  );
};

export default QuestionInput;
