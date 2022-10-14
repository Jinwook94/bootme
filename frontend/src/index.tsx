import { ThemeProvider } from 'styled-components';
import { theme } from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';

import App from './App';
import ReactDOM from 'react-dom';

const rootElement = document.getElementById('root');


// 참고
// https://mswjs.io/docs/getting-started/integrate/browser#:~:text=Import%20the,the%20examples%20below%3A
// https://github.com/woowacourse-teams/2022-smody/blob/fd880f2d02c27d185206b121c1eee849a06b5deb/frontend/src/index.tsx

if (process.env.NODE_ENV === 'development') {
    const { worker } = require('./mocks/browser')
    worker.start()
}

ReactDOM.render(
    <ThemeProvider theme={theme}>
        <GlobalStyle />
        <App />
    </ThemeProvider>,
    rootElement
);
