import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

type CreateIssuePageType = {
  width?: string;
  height?: string;
};

export const CreateIssuePageBox = styled.div<CreateIssuePageType>`
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  ${flexLayoutMixin('row', 'center', '')};
  margin-top: 24px;
  color: ${({ theme }) => theme.color.basic}; ;
`;

export const SideBar = styled.div``;
