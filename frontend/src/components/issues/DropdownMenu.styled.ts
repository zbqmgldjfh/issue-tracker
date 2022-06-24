import { flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

type PropsType = {
  isClicked: boolean;
  width: string;
};

export const MenuBox = styled.div<PropsType>`
  display: ${({ isClicked }) => (isClicked ? 'block' : 'none')};
  width: ${({ width }) => width};
  position: absolute;
  top: 30px;
  right: 0;
  border: 1px solid #444c56;
  background-color: #2b3139;
`;

export const Header = styled.header`
  ${flexLayoutMixin('row', 'space-between')};
  font-size: 0.75rem;
  color: #97a4b0;
  padding: 7px 16px;

  svg:hover {
    fill: #a9b7c4;
    cursor: pointer;
  }
`;

export const Title = styled.div`
  line-height: 19px;
  font-weight: 800;
`;

export const Main = styled.main`
  ${flexLayoutMixin('column', 'center', 'center')};
`;

export const Input = styled.input`
  font-size: 0.875rem;
  line-height: 20px;
  background-color: #22272e;
  padding: 5px 12px;
  border: 1px solid #444c56;
  &:focus {
    border: 1px solid #539bf5;
  }
  width: 95%;
`;
