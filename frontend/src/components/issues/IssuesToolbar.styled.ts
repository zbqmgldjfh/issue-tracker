import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

export const ToolbarBox = styled.div`
  ${flexLayoutMixin('row', 'space-between', 'center')}
  width: 100%;
  background-color: ${({ theme }) => theme.background.primary};
  padding: 16px;
  color-scheme: dark;
`;

export const ToggleBox = styled.div`
  ${flexLayoutMixin('row', 'center', 'center')}
  gap: 13px;
`;

export const Toggle = styled.div`
  ${flexLayoutMixin('row', 'center', 'center')}
  gap: 7px;
  color: ${({ theme }) => theme.color.bold};
  fill: ${({ theme }) => theme.color.bold};
  &:hover {
    color: ${({ theme }) => theme.color.basic};
    fill: ${({ theme }) => theme.color.basic};
    cursor: pointer;
  }
`;

export const OpenText = styled.div`
  height: 16px;
  line-height: 19px;
`;

export const ClosedText = styled.div`
  height: 16px;
  line-height: 19px;
`;

export const DropdownList = styled.div`
  ${flexLayoutMixin('row', 'center', 'center')}
  color: ${({ theme }) => theme.color.bold};
  fill: ${({ theme }) => theme.color.bold};
  font-size: 0.875rem;
  div:hover {
    color: ${({ theme }) => theme.color.basic};
    fill: ${({ theme }) => theme.color.basic};
    cursor: pointer;
  }
`;
