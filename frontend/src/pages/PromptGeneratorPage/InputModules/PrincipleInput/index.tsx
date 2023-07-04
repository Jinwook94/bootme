import { Box, CloseButton, Flex, MultiSelect, MultiSelectValueProps, rem, SelectItemProps } from '@mantine/core';
import React, { forwardRef, useEffect } from 'react';
import { useStacks } from '../../../../hooks/useStacks';
import { usePrompt } from '../../../../hooks/usePrompt';

const PrincipleInput = () => {
  const { setTechStack } = usePrompt();
  const { loadingStackList, fetchStacks, icons, stackList } = useStacks();

  function Value({ value, label, onRemove, ...others }: MultiSelectValueProps & { value: string }) {
    const IconSrc = icons[value];
    return (
      <div {...others}>
        <Box
          sx={theme => ({
            display: 'flex',
            cursor: 'default',
            alignItems: 'center',
            backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.white,
            border: `${rem(1)} solid ${theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.colors.gray[4]}`,
            paddingLeft: theme.spacing.xs,
            borderRadius: theme.radius.sm,
          })}
        >
          <Box mr={10}>
            <img src={IconSrc} alt={value} width="18" height="18" />
          </Box>
          <Box sx={{ lineHeight: 1, fontSize: rem(12) }}>{label}</Box>
          <CloseButton onMouseDown={onRemove} variant="transparent" size={22} iconSize={14} tabIndex={-1} />
        </Box>
      </div>
    );
  }

  const Item = forwardRef<HTMLDivElement, SelectItemProps>(({ label, value, ...others }, ref) => {
    const IconSrc = value ? icons[value] : '';
    return (
      <div ref={ref} {...others}>
        <Flex align="center">
          <Box mr={10}>
            <img src={IconSrc} alt={value} width="18" height="18" />
          </Box>
          <div>{label}</div>
        </Flex>
      </div>
    );
  });

  useEffect(() => {
    fetchStacks().catch();
    // fetchPrinciples().catch();
  }, []);

  return (
    <div>
      <span>관점 & 원칙</span>
      {!loadingStackList && (
        <MultiSelect
          data={stackList}
          limit={20}
          valueComponent={Value}
          itemComponent={Item}
          nothingFound={
            <>
              검색된 관점 & 원칙이 없습니다.
              <br />
              관련 관점 & 원칙을 설명란에 직접 입력 해주세요.
            </>
          }
          placeholder="OOP, DDD..."
          searchable
          clearable
          maxDropdownHeight={300}
          mt={6}
          onChange={value => setTechStack(value)}
        />
      )}
    </div>
  );
};

export default PrincipleInput;
