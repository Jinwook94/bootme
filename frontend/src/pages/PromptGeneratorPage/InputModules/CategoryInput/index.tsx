import { usePrompt } from '../../../../hooks/usePrompt';
import { Group, Radio } from '@mantine/core';
import React from 'react';

const CategoryInput = () => {
  const { category, setCategory } = usePrompt();
  return (
    <Radio.Group label="분류" fw={400} value={category} onChange={setCategory}>
      <Group mt={6}>
        <Radio value="general" label="일반 질문" />
        <Radio value="feature" label="기능 구현" />
        <Radio value="apiDesign" label="API 설계" />
      </Group>
    </Radio.Group>
  );
};

export default CategoryInput;
