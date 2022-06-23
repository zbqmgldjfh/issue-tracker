import { flexLayoutMixin } from 'src/utils/utils';
import styled, { css } from 'styled-components';

type TabType = {
  active?: boolean;
};

export const CommentHeaderBox = styled.div`
  ${flexLayoutMixin('row', 'space-between', 'center')};
`;

export const CommentHeaderTabNav = styled.div`
  ${flexLayoutMixin('row', 'start', 'center')};
`;

export const CommentHeaderButtons = styled.div`
  ${flexLayoutMixin('row', '', '')};
`;

export const Tab = styled.div<TabType>`
  ${({ active }) =>
    active &&
    css`
      border: 2px solid ${({ theme }) => theme.border.primary};
      z-index: 1;
      border-bottom: none;
    `};
  padding: 16px;
  font-size: 0.875rem;
`;
