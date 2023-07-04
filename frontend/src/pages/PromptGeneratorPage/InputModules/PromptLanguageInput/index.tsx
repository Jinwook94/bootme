import { usePrompt } from '../../../../hooks/usePrompt';
import { Group, Radio } from '@mantine/core';
import React from 'react';

const PromptLanguageInput = () => {
  const { promptLanguage, setPromptLanguage } = usePrompt();
  return (
    <Radio.Group
      defaultValue="english"
      label="프롬프트 언어"
      fw={400}
      value={promptLanguage}
      onChange={setPromptLanguage}
    >
      <Group mt={6}>
        <Radio value="english" label="영어" />
        <Radio value="korean" label="한국어" />
      </Group>
    </Radio.Group>
  );
};

export default PromptLanguageInput;
