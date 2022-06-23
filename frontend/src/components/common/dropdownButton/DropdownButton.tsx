import React from 'react';
import DropdownCaret from 'src/assets/DropdownCaret.svg';
import ButtonBox from './DropdownButton.styled';

type propTypes = {
  text: string;
};

export default function DropdownButton({ text }: propTypes) {
  return (
    <ButtonBox>
      <div>{text}</div>
      <DropdownCaret />
    </ButtonBox>
  );
}
