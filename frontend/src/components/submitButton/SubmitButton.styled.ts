import styled from 'styled-components';

export type SubmitButtonType = {
  width: string;
  height: string;
};

export const SubmitButtonBox = styled.button<SubmitButtonType>`
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  background-color: ${({ theme }) => theme.background.green};
  color: ${({ theme }) => theme.color.white};
  border-radius: 5px;
  cursor: pointer;
`;
