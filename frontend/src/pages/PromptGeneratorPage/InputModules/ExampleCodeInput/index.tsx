import { Textarea } from '@mantine/core';
import React from 'react';
import { usePrompt } from '../../../../hooks/usePrompt';

const ExampleInput = () => {
  const { exampleCode, setExampleCode } = usePrompt();
  return (
    <div>
      <span>예시 코드</span>
      <Textarea
        aria-label="exampleCode"
        placeholder="예시 코드"
        autosize
        minRows={2}
        maxRows={20}
        radius="sm"
        mt={6}
        value={exampleCode}
        onChange={e => setExampleCode(e.currentTarget.value)}
      />
    </div>
  );
};

export default ExampleInput;
