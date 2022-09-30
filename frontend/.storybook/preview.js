import { ThemeProvider } from 'styled-components';
import { MemoryRouter } from 'react-router';
import GlobalStyle from '../src/styles/GlobalStyle';
import { theme } from '../src/styles/theme';

//참고: https://github.com/woowacourse-teams/2022-ternoko/blob/develop/frontend/.storybook/preview.js

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
};

export const decorators = [
  (Story) => (
      <MemoryRouter initialEntries={['/']}>
        <ThemeProvider theme={theme}>
          <GlobalStyle />
          <Story />
        </ThemeProvider>
      </MemoryRouter>
  ),
];