import React from 'react';
import ItemsNumber from '../itemsNumber/itemsNumber';
import AttributeBox from './IssueAttribute.styled';

type propsType = {
  SVG: any;
  text: string;
  number: number;
};

export default function IssueAttribute({ SVG, text, number }: propsType) {
  return (
    <AttributeBox>
      {<SVG />}
      {text}
      <ItemsNumber number={number} />
    </AttributeBox>
  );
}
