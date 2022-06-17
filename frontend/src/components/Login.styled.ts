import { setBackgroundColor, flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

type LoginBoxType = {
  backgroundColor: string;
};

export const FlexBox = styled.div`
  ${flexLayoutMixin('column', 'center', 'center')};
  width: 342px;
  height: 100%;
  gap: 16px;

  p {
    font-weight: 700;
    color: #a0a3bd;
  }
`;

export const Title = styled.div`
  font-family: 'Montserrat';
  font-style: italic;
  font-weight: 400;
  font-size: 3.5rem;
  line-height: 72px;
  margin-bottom: 53px;
  color: #14142b;
`;

export const LoginBox = styled.button<LoginBoxType>`
  ${flexLayoutMixin('column', 'center', 'center')};
  width: 340px;
  height: 64px;
  font-size: 1.125rem;
  padding: 16px 24px;
  ${({ backgroundColor }) => setBackgroundColor(backgroundColor)};
  color: white;
  border-radius: 16px;
  cursor: pointer;
  margin: 8px 0;
`;

export const InputBox = styled.div`
  ${flexLayoutMixin('column', 'center', 'center')};
  width: 340px;
  height: 64px;
  padding: 8px 24px;
  background-color: #eff0f6;
  border-radius: 16px;

  input {
    outline: none;
    border: 0;
    line-height: 28px;
    width: 100%;
    background-color: #eff0f6;
  }
`;

export const SignUp = styled.div`
  color: #4e4b66;
  font-size: 0.75rem;
  cursor: pointer;
`;
