import { setBackgroundColor, flexLayoutMixin } from 'src/utils/utils';
import styled from 'styled-components';

type LoginBoxType = {
  loginType: string;
};

export const FlexBox = styled.div`
  ${flexLayoutMixin('column', 'center', 'center')};
  width: 342px;
  height: 100%;
  gap: 16px;

  p {
    font-weight: 700;
    color: ${({ theme }) => theme.color.subText};
  }
`;

export const Title = styled.div`
  font-family: 'Montserrat';
  font-style: italic;
  font-weight: 400;
  font-size: 3.5rem;
  line-height: 72px;
  margin-bottom: 53px;
  color: ${({ theme }) => theme.color.primary}; ;
`;

export const LoginBox = styled.button<LoginBoxType>`
  ${flexLayoutMixin('column', 'center', 'center')};
  width: 340px;
  height: 64px;
  font-size: 1.125rem;
  padding: 16px 24px;
  ${({ loginType }) => setBackgroundColor(loginType === 'Github' ? '#14142b' : '#007aff')};
  color: ${({ theme }) => theme.color.primary};
  border-radius: 16px;
  border: 0;
  cursor: pointer;
  margin: 8px 0;
  &:hover {
    ${({ loginType }) => setBackgroundColor(loginType === 'Github' ? '#212121' : '#004de3')};
  }
`;

export const InputBox = styled.div`
  ${flexLayoutMixin('column', 'center', 'center')};
  width: 340px;
  height: 64px;
  padding: 8px 24px;
  background-color: ${({ theme }) => theme.background.complementary};
  border-radius: 16px;

  input {
    outline: none;
    border: 0;
    line-height: 28px;
    width: 100%;
    background-color: ${({ theme }) => theme.background.complementary};
  }
`;

export const SignUp = styled.div`
  color: ${({ theme }) => theme.color.primary};
  font-size: 0.75rem;
  cursor: pointer;
`;
