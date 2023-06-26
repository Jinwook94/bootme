import { createStyles, Flex, Paper, rem, SimpleGrid, Skeleton } from '@mantine/core';
import { Bookmark, CompanyNameWrapper, CourseTags, CourseTitleWrapper, ItemBody, ItemHeader, Wrapper } from './style';
import { CourseCardListStyle } from '../CourseCardList/style';
import React from 'react';

const CourseCardSkeleton = () => {
  const useStyles = createStyles(theme => ({
    card: {
      position: 'relative',
      cursor: 'pointer',
      overflow: 'hidden',
      transition: 'transform 150ms ease, box-shadow 100ms ease',

      '&:hover': {
        boxShadow: theme.shadows.md,
        transform: 'scale(1.02)',
      },

      '&::before': {
        content: '""',
        position: 'absolute',
        top: 0,
        bottom: 0,
        left: 0,
        width: rem(6),
      },
    },
  }));

  const { classes } = useStyles();

  return (
    <CourseCardListStyle>
      <SimpleGrid
        cols={2}
        spacing="xs"
        verticalSpacing="xs"
        style={{ width: '100%' }}
        breakpoints={[{ maxWidth: '62rem', cols: 1 }]}
      >
        {Array.from({ length: 12 }).map((_, i) => (
          <div key={i} style={{ display: 'flex', width: '100%', margin: '0 auto' }}>
            <Paper style={{ width: '100%' }} withBorder radius="md" className={classes.card}>
              <Wrapper>
                <ItemHeader>
                  <Skeleton circle style={{ width: '100%', height: '100%', borderRadius: '8px' }} />
                </ItemHeader>
                <ItemBody>
                  <CourseTitleWrapper>
                    <Skeleton height={27} radius="xl" width="100%" style={{ borderRadius: '8px' }} />
                  </CourseTitleWrapper>
                  <CompanyNameWrapper>
                    <Flex direction={'column'} mt={8} gap={8} style={{ width: '100%', height: '100%' }}>
                      <Skeleton height={21} radius="xl" width="70%" style={{ borderRadius: '8px' }} />
                      <Skeleton height={21} radius="xl" width="70%" style={{ borderRadius: '8px' }} />
                      <Skeleton height={21} radius="xl" width="70%" style={{ borderRadius: '8px' }} />
                    </Flex>
                  </CompanyNameWrapper>
                  <CourseTags>
                    <Skeleton
                      height={8}
                      mt={6}
                      radius="xl"
                      style={{ width: '100%', height: '100%', borderRadius: '8px' }}
                    />
                  </CourseTags>
                </ItemBody>
                <Bookmark></Bookmark>
              </Wrapper>
            </Paper>
          </div>
        ))}
      </SimpleGrid>
    </CourseCardListStyle>
  );
};

export default CourseCardSkeleton;
