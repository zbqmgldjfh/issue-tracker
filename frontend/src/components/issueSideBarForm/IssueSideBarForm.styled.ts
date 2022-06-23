import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

type ContentType = {
  styles: string;
};

export const IssueSideBarFormBox = styled.div`
  position: relative;
  margin-top: 24px;
  font-size: 0.75rem;
  &:first-child {
    margin-top: 0;
  }
`;

export const IssueSideBarHeader = styled.div`
  ${flexLayoutMixin('row', 'space-between', 'center')};
  margin-bottom: 20px;
  fill: ${({ theme }) => theme.fill.header};
  cursor: pointer;
`;

export const IssueSideBarFormContent = styled.div<ContentType>`
  ${({ styles }) => styles};
  padding-bottom: 20px;
  border-bottom: 1px solid ${({ theme }) => theme.border.deep};
`;
