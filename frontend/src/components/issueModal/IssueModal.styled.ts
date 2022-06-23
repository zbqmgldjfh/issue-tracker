import styled from 'styled-components';

export const IssueModalBox = styled.div`
  position: absolute;
  background-color: ${({ theme }) => theme.background.deep};
  border: 1px solid ${({ theme }) => theme.border.primary};
  border-radius: 5px;
  z-index: 1;
  top: 25px;
  right: 0px;
  width: 298px;
  height: 120px;
`;

export const Header = styled.div`
  height: 32px;
  padding: 10px;
  color: ${({ theme }) => theme.color.primary};
  border-bottom: 1px solid ${({ theme }) => theme.border.primary}; ;
`;

export const Main = styled.div`
  padding: 10px;
  color: ${({ theme }) => theme.color.weak};
  border-bottom: 1px solid ${({ theme }) => theme.border.primary}; ;
`;

export const Bottom = styled.div`
  padding: 15px 10px;
  color: ${({ theme }) => theme.fill.header}; ;
`;

export const SearchBarBox = styled.input.attrs({ type: 'textarea' })`
  width: 278px;
  height: 28px;
  padding: 10px 15px;
  border: 1px solid ${({ theme }) => theme.border.primary};
  border-radius: 5px;
  background-color: ${({ theme }) => theme.background.secondary};
  color: ${({ theme }) => theme.color.basic};
  &:focus {
    border: 1px solid ${({ theme }) => theme.border.focus};
  }
  &:focus-visible {
    outline: none;
  }
`;
