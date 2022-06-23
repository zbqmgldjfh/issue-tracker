import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

type CreateIssueType = {
  width?: string;
  height?: string;
};

export const CreateIssueBox = styled.div<CreateIssueType>`
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  border: 1px solid ${({ theme }) => theme.border.secondary};
  border-radius: 5px;
  margin-right: 24px;
`;

export const CommentAreaBox = styled.div`
  padding: 8px;
`;

export const CommentAreaMain = styled.div`
  margin-top: 10px;
`;

export const CommentAreaFooter = styled.div`
  ${flexLayoutMixin('row', 'space-between', '')};
  margin-top: 10px;
`;
