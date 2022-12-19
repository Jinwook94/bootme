import styled, { css } from 'styled-components';

const FlexBox = styled.div<FlexBoxProps>`
  display: flex;
  ${({ flexDirection, flexWrap, justifyContent, alignItems, gap }) => css`
    flex-direction: ${flexDirection};
    flex-wrap: ${flexWrap};
    justify-content: ${justifyContent};
    align-items: ${alignItems};
    gap: ${gap};
  `}
`;

interface FlexBoxProps {
  flexDirection?: 'row' | 'column';
  justifyContent?:
    | 'flex-start'
    | 'flex-end'
    | 'center'
    | 'space-between'
    | 'space-around'
    | 'initial'
    | 'inherit'
    | 'space-evenly';
  flexWrap?: 'wrap' | 'nowrap' | 'wrap-reverse';
  alignItems?: 'stretch' | 'center' | 'flex-start' | 'flex-end' | 'baseline' | 'initial' | 'inherit';
  gap?: string;
}

export default FlexBox;
