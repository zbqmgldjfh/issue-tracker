import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle`
  ${reset};
  *{
    box-sizing:border-box;
    user-select: none; 
  }
  body{
    background-color:#E6E6E6;
  }
`;

export default GlobalStyle;
