import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

export const HeaderBox = styled.div`
  ${flexLayoutMixin('row', 'center', 'center')};
  width: 63%;
  color: ${({ theme }) => theme.color.basic};
  fill: ${({ theme }) => theme.color.basic};
  gap: 16px;
`;

export const FilterBox = styled.div`
  ${flexLayoutMixin('row', 'space-between', 'center')}
  line-height: 23px;
  flex-grow: 1;
  color: ${({ theme }) => theme.color.primary};
`;

export const FilterButton = styled.div`
  ${flexLayoutMixin('row', 'space-between', 'center')}
  width: 90px;
  padding: 5px 16px;
  font-weight: 500;
  background-color: ${({ theme }) => theme.background.box};
  border-radius: 6px 0 0 6px;
  &:hover {
    background-color: ${({ theme }) => theme.background.third};
    cursor: pointer;
  }
`;

export const FilterInput = styled.input`
  padding: 5px 16px;
  line-height: 19px;
  background-color: ${({ theme }) => theme.background.secondary};
  box-sizing: border-box;
  border: 2px solid ${({ theme }) => theme.border.primary};
  border-radius: 0 6px 6px 0;
  flex-grow: 1;
  color: ${({ theme }) => theme.color.bold};
  font-size: 0.875rem;
  &:focus {
    box-sizing: border-box;
    outline: 0;
    border: 2px solid ${({ theme }) => theme.border.focus};
  }
`;

export const AttributesBox = styled.div`
  ${flexLayoutMixin('row', 'center', 'center')};
  border-radius: 0 6px 6px 0;
`;

export const NewIssueButton = styled.div`
  background-color: ${({ theme }) => theme.background.green};
  padding: 5px 16px;
  font-size: 0.875rem;
  font-weight: 500;
  color: ${({ theme }) => theme.color.white};
  line-height: 23px;
  border-radius: 6px;
  white-space: nowrap;
  &:hover {
    background-color: ${({ theme }) => theme.background.lightGreen};
    cursor: pointer;
  }
`;
