import { Group, Radio } from '@mantine/core';
import React from 'react';
import { usePrompt } from '../../../../hooks/usePrompt';

const ResponseFormatInput = () => {
  const { responseFormat, setResponseFormat } = usePrompt();
  return (
    <Radio.Group defaultValue="default" label="답변 형식" fw={400} value={responseFormat} onChange={setResponseFormat}>
      <Group mt={6}>
        <Radio value="default" label="기본" />
        <Radio value="conversation" label="대화형" />
      </Group>
    </Radio.Group>
  );
};

export default ResponseFormatInput;
