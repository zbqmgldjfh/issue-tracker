import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

const LoginPageBox = styled.div`
  height: 100vh;
  ${flexLayoutMixin('row', 'center', 'center')}
  box-sizing: border-box;
`;

export default LoginPageBox;
