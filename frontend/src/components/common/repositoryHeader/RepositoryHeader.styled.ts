import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

export const RepositoryHeaderBox = styled.div`
  ${flexLayoutMixin('column', 'space-between')};
  padding: 16px 32px;
  font-size: 1.25rem;
  fill: ${({ theme }) => theme.fill.header};
  color: ${({ theme }) => theme.color.basic};
  gap: 20px;
  border-bottom: 1px solid ${({ theme }) => theme.border.primary}; ;
`;

export const TopArea = styled.div`
  ${flexLayoutMixin('row', 'space-between')};
`;

export const BottomArea = styled.div`
  ${flexLayoutMixin('row')};
  gap: 7px;
`;

export const ContentArea = styled.div`
  ${flexLayoutMixin('row')};
  gap: 7px;
`;

export const Status = styled.div`
  ${flexLayoutMixin('', 'center', 'center')}
  background-color: ${({ theme }) => theme.background.box};
  padding: 3px 12px;
  border: 1px solid ${({ theme }) => theme.border.primary};
  border-radius: 5px;
  font-size: 0.75rem;
  gap: 5px;
  line-height: 17px;
  color: ${({ theme }) => theme.color.primary};
  &:hover {
    background-color: ${({ theme }) => theme.background.third};
    border: 1px solid ${({ theme }) => theme.border.hover};
    cursor: pointer;
  }
`;

export const TitleText = styled.div`
  font-size: 1.25rem;
  color: ${({ theme }) => theme.color.link};
  margin: 0 5px;
  &:hover {
    text-decoration: underline;
    cursor: pointer;
  }
`;

export const StatusText = styled.div`
  font-size: 0.75rem;
  font-weight: 600;
  margin-left: 5px;
  height: 19px;
  line-height: 22px;
`;
