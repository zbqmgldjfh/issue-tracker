import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

const HeaderBox = styled.div`
  ${flexLayoutMixin('row', 'space-between', 'center')}
  padding: 16px 32px;
  background-color: ${({ theme }) => theme.background.primary};
  color: ${({ theme }) => theme.color.primary};
  fill: ${({ theme }) => theme.color.primary};
  svg:hover {
    cursor: pointer;
    fill: ${({ theme }) => theme.color.hover};
  }
`;

const FlexBox = styled.div`
  ${flexLayoutMixin('row', 'space-between', 'center')}
  gap: 16px;
`;

const SearchBox = styled.div`
  input {
    font-size: 0.875rem;
    min-height: 28px;
    padding: 3px 12px;
    background-color: ${({ theme }) => theme.background.secondary};
    border: none;
    color: ${({ theme }) => theme.color.basic};
  }
  input::placeholder {
    color: ${({ theme }) => theme.color.basic};
  }
`;

const NavBar = styled.ul`
  ${flexLayoutMixin('row', 'space-between', 'center')}
  font-weight: 600;
  gap: 16px;

  li:hover {
    cursor: pointer;
    color: ${({ theme }) => theme.color.hover};
  }
`;

const Thumbnail = styled.div``;

export { HeaderBox, SearchBox, NavBar, FlexBox, Thumbnail };
