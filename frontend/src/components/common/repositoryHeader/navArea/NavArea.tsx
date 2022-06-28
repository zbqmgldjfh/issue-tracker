import React from 'react';
import { Icon, IconPropType } from '../../icon/Icon';
import { NavBox, NavText } from './NavArea.styled';

type NavAreaType = {
  content: IconPropType;
};

export default function NavArea({ content }: NavAreaType) {
  return (
    <NavBox>
      <Icon name={content} />
      <NavText>{content}</NavText>
    </NavBox>
  );
}
