import React from 'react';
import Loading from 'src/assets/loading.gif';
import { LoadingBox, LoadingText } from './LoadingPage.styled';

export default function LoadingPage() {
  return (
    <LoadingBox>
      <img src={Loading} alt="로딩중"></img>
      <LoadingText>Loading ...</LoadingText>
    </LoadingBox>
  );
}
