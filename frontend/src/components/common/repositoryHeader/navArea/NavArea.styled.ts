import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

export const NavBox = styled.div`
  ${flexLayoutMixin('', 'center', 'center')}
  gap: 7px;
  padding: 6px 10px;
  &:hover {
    background-color: ${({ theme }) => theme.background.third};
    border-radius: 7px;
    cursor: pointer;
  }
`;

export const NavText = styled.div`
  font-size: 0.875rem;
  font-weight: 600;
  margin-left: 5px;
  line-height: 7px;
`;
