import React from 'react';
import { NavBox, NavText } from './NavArea.styled';

type NavAreaType = {
  text: string;
  SVG: any;
};

export default function NavArea({ text, SVG }: NavAreaType) {
  return (
    <NavBox>
      {<SVG />}
      <NavText>{text}</NavText>
    </NavBox>
  );
}
