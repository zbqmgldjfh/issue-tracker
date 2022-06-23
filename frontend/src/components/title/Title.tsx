import React from 'react';
import { TitleBox, TitleBoxType } from './Title.styled';

export default function Title({ width, height }: TitleBoxType) {
  return <TitleBox width={width} height={height} placeholder="Title" />;
}
