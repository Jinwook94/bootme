import { Textarea } from '@mantine/core';
import React from 'react';
import { usePrompt } from '../../../../hooks/usePrompt';

const ErdInput = () => {
  const { erd, setErd } = usePrompt();
  return (
    <div>
      <span>ERD</span>
      <Textarea
        aria-label="erd"
        placeholder="ERD를 입력하세요 (dbdiagram.io)"
        autosize
        minRows={2}
        maxRows={20}
        radius="sm"
        mt={6}
        value={erd}
        onChange={e => setErd(e.currentTarget.value)}
      />
    </div>
  );
};

export default ErdInput;
