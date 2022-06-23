import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

const ButtonBox = styled.div`
  ${flexLayoutMixin('', 'center', 'center')}
  height: 32px;
  font-size: 0.875rem;
  padding: 5px 16px;
  gap: 5px;
`;

export default ButtonBox;
