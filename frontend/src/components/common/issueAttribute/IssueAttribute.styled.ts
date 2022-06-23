import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

const AttributeBox = styled.div`
  ${flexLayoutMixin('row', 'center', 'center')}
  padding: 5px 16px;
  gap: 3px;
  border: 2px solid ${({ theme }) => theme.border.primary};
  border-radius: 6px;
  &:hover {
    background-color: ${({ theme }) => theme.background.primary};
    cursor: pointer;
  }
`;

export default AttributeBox;
