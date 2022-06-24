import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

export const ButtonBox = styled.div`
  ${flexLayoutMixin('', 'center', 'center')}
  position: relative;
  height: 32px;
  font-size: 0.875rem;
  padding: 5px 16px;
  gap: 5px;
`;

export const Button = styled.div`
  &:hover {
    color: ${({ theme }) => theme.color.basic};
    fill: ${({ theme }) => theme.color.basic};
    cursor: pointer;
  }
`;
