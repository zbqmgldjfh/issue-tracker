import styled from 'styled-components';

export type CommentBoxType = {
  width: string;
  height: string;
};

export const CommentBox = styled.textarea<CommentBoxType>`
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  padding: 10px 15px;
  border: 2px solid ${({ theme }) => theme.border.primary};
  border-radius: 5px;
  background-color: ${({ theme }) => theme.background.secondary};
  color: ${({ theme }) => theme.color.basic};
  &:focus {
    border: 2px solid ${({ theme }) => theme.border.focus};
  }
  &:focus-visible {
    outline: none;
  }
`;
