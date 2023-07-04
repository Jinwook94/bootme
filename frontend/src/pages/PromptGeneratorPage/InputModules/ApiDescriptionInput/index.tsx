import { Textarea } from '@mantine/core';
import React from 'react';
import { usePrompt } from '../../../../hooks/usePrompt';
import { Asterisk } from '../../style';

const ApiDescriptionInput = () => {
  const { apiDescription, setApiDescription } = usePrompt();
  return (
    <div>
      <>
        <span>API 설명</span>
        <Asterisk>*</Asterisk>
      </>
      <Textarea
        aria-label="apiDescription"
        placeholder="구현하려는 API에 대한 설명을 입력하세요."
        autosize
        minRows={2}
        maxRows={20}
        radius="sm"
        mt={6}
        value={apiDescription}
        onChange={e => setApiDescription(e.currentTarget.value)}
      />
    </div>
  );
};

export default ApiDescriptionInput;
