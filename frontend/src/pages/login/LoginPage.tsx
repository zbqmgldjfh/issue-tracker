// import Login from 'components/Login';
import React from 'react';
import Login from 'src/components/login/Login';
import LoginPageBox from './LoginPage.styled';

export default function LoginPage() {
  return (
    <LoginPageBox>
      <Login />
    </LoginPageBox>
  );
}
