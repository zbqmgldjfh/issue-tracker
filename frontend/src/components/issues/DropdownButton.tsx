import React from 'react';
import { useDispatch } from 'react-redux';
import DropdownCaret from 'src/assets/DropdownCaret.svg';
import { changeTab } from 'src/modules/clickedTab';
import { ButtonBox, Button } from './DropdownButton.styled';
import DropdownMenu from './DropdownMenu';

type propsType = {
  modalWidth: string;
  text: string;
  makeItem: any;
};

export default function DropdownButton({ modalWidth, text, makeItem }: propsType) {
  const dispatch = useDispatch();

  return (
    <ButtonBox>
      <Button onClick={() => dispatch(changeTab(text))}>
        {text}
        <DropdownCaret />
      </Button>
      <DropdownMenu modalWidth={modalWidth} detail={text} makeItem={makeItem} />
    </ButtonBox>
  );
}
