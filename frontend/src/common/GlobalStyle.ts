import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle`
  ${reset};
  *{
    box-sizing: border-box;
    user-select: none; 
  }
  body {
    background-color: ${({ theme }) => theme.background.basic};
  }
`;

export default GlobalStyle;
