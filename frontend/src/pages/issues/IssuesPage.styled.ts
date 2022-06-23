import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

const IssuesPageBox = styled.div`
  ${flexLayoutMixin('column', 'center', 'center')}
  width: 100%;
  padding: 24px 0;
  gap: 16px;
`;

export default IssuesPageBox;
