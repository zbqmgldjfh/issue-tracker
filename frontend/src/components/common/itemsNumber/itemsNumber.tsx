import React from 'react';
import ItemsNumberBox from './itemsNumber.styled';

type PropsType = {
  number: number;
};

export default function ItemsNumber({ number }: PropsType) {
  return <ItemsNumberBox>{number}</ItemsNumberBox>;
}
