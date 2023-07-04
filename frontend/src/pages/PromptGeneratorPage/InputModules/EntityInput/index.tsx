import { Textarea } from '@mantine/core';
import React from 'react';
import { usePrompt } from '../../../../hooks/usePrompt';

const EntityInput = () => {
  const { entity, setEntity } = usePrompt();
  return (
    <div>
      <span>엔티티 or 모델 코드</span>
      <Textarea
        aria-label="entity"
        placeholder="DB 테이블과 매핑되는 코드를 입력하세요. (엔티티 클래스, 모델 클래스)"
        autosize
        minRows={2}
        maxRows={20}
        radius="sm"
        mt={6}
        value={entity}
        onChange={e => setEntity(e.currentTarget.value)}
      />
    </div>
  );
};

export default EntityInput;
