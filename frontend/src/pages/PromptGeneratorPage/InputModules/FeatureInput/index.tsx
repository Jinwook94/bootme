import { usePrompt } from '../../../../hooks/usePrompt';
import { Textarea } from '@mantine/core';
import React from 'react';
import { Asterisk } from '../../style';

const FeatureInput = () => {
  const { featureDescription, setFeatureDescription } = usePrompt();
  return (
    <div>
      <>
        <span>기능 설명</span>
        <Asterisk>*</Asterisk>
      </>
      <Textarea
        aria-label="featureDescription"
        placeholder="기능 설명을 입력하세요."
        autosize
        minRows={2}
        maxRows={20}
        radius="sm"
        mt={6}
        value={featureDescription}
        onChange={e => setFeatureDescription(e.currentTarget.value)}
      />
    </div>
  );
};

export default FeatureInput;
