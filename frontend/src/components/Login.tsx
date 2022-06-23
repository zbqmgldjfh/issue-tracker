import React from 'react';
import { FlexBox, InputBox, LoginBox, SignUp, Title } from './Login.styled';

export default function Login() {
  return (
    <FlexBox>
      <Title>Issue Tracker</Title>
      <LoginBox loginType={'Github'}>Github 계정으로 로그인</LoginBox>
      <p>or</p>
      <InputBox>
        <input placeholder="아이디" type="text"></input>
      </InputBox>
      <InputBox>
        <input placeholder="비밀번호" type="password"></input>
      </InputBox>
      <LoginBox loginType={'Id'}>아이디로 로그인</LoginBox>
      <SignUp>회원가입</SignUp>
    </FlexBox>
  );
}
