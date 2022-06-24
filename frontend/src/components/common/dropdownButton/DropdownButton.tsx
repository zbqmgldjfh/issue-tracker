import React from 'react';
import { useDispatch } from 'react-redux';
import DropdownCaret from 'src/assets/DropdownCaret.svg';
import { changeTab } from 'src/modules/clickedTab';
import ButtonBox from './DropdownButton.styled';

type propTypes = {
  text: string;
};

export default function DropdownButton({ text }: propTypes) {
  const dispatch = useDispatch();

  return (
    <ButtonBox onClick={() => dispatch(changeTab(text))}>
      <div>{text}</div>
      <DropdownCaret />
    </ButtonBox>
  );
}
