import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

export const IssuesItemBox = styled.div`
  ${flexLayoutMixin('row', 'space-between')}
  width: 100%;
  color-scheme: dark;
  padding: 16px;
  gap: 15px;
  fill: ${({ theme }) => theme.fill.green};
  &:hover {
    background-color: ${({ theme }) => theme.background.primary};
  }
`;

export const ItemMain = styled.div`
  ${flexLayoutMixin('column', 'center')};
  gap: 10px;
`;

export const Title = styled.span`
  font-weight: 600;
  color: ${({ theme }) => theme.color.basic};
  &:hover {
    color: ${({ theme }) => theme.color.lightBlue};
    cursor: pointer;
  }
`;

export const OpenedBy = styled.span`
  color: ${({ theme }) => theme.color.bold};
  font-size: 0.75rem;
`;

export const FlexBox = styled.div`
  flex-grow: 1;
`;

export const Assignee = styled.div`
  padding: 0 20px;
  img {
    width: 20px;
    height: 20px;
    border-radius: 999px;
  }
  &:hover {
    cursor: pointer;
  }
`;
export const Review = styled.div`
  ${flexLayoutMixin('row', 'center', 'flex-start')}
  fill: ${({ theme }) => theme.color.bold};
  padding: 0 20px;
  gap: 2px;
  line-height: 17px;
  color: ${({ theme }) => theme.color.bold};
  &:hover {
    fill: ${({ theme }) => theme.color.lightBlue};
    color: ${({ theme }) => theme.color.lightBlue};
    cursor: pointer;
  }
`;

export const ReviewNum = styled.span`
  font-size: 0.75rem;
`;
