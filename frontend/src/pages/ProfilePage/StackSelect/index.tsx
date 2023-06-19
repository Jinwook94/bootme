import { Box, CloseButton, Flex, MultiSelect, MultiSelectValueProps, rem, SelectItemProps } from '@mantine/core';
import React, { forwardRef, useEffect } from 'react';
import './style.css';
import { useProfile } from '../../../hooks/useProfile';
import { useStacks } from '../../../hooks/useStacks';

const StackSelect = () => {
  const { loadingStackList, fetchStacks, icons, stackList } = useStacks();
  const { stacks, setStacks } = useProfile();

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
  }, []);

  return (
    <>
      {!loadingStackList && (
        <MultiSelect
          mt="xl"
          data={stackList}
          limit={20}
          valueComponent={Value}
          itemComponent={Item}
          searchable
          nothingFound="검색된 기술 스택이 없습니다."
          clearable
          maxDropdownHeight={300}
          defaultValue={stacks}
          placeholder="사용하시는 기술 스택을 선택하세요."
          label="기술 스택"
          onChange={value => setStacks(value)}
        />
      )}
    </>
  );
};

export default StackSelect;
