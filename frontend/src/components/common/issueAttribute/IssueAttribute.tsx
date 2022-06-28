import React from 'react';
import { Icon, IconPropType } from '../icon/Icon';
import ItemsNumber from '../itemsNumber/itemsNumber';
import AttributeBox from './IssueAttribute.styled';

type propsType = {
  text: IconPropType;
  number: number;
};

export default function IssueAttribute({ text, number }: propsType) {
  return (
    <AttributeBox>
      <Icon name={text} />
      {text}
      <ItemsNumber number={number} />
    </AttributeBox>
  );
}
