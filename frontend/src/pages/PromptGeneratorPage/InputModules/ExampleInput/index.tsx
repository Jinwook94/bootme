import { Textarea } from '@mantine/core';
import React from 'react';
import { usePrompt } from '../../../../hooks/usePrompt';

const ExampleInput = () => {
  const { example, setExample } = usePrompt();
  return (
    <div>
      <span>예시</span>
      <Textarea
        aria-label="example"
        placeholder="질문 관련 예시"
        autosize
        minRows={2}
        maxRows={20}
        radius="sm"
        mt={6}
        value={example}
        onChange={e => setExample(e.currentTarget.value)}
      />
    </div>
  );
};

export default ExampleInput;
