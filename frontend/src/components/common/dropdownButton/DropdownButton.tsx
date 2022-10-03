import React from 'react';
import { useDispatch } from 'react-redux';
import { changeTab } from 'src/modules/clickedTab';
import { Icon } from 'src/components/common/icon/Icon';
import ButtonBox from './DropdownButton.styled';

type propTypes = {
  text: string;
};

export default function DropdownButton({ text }: propTypes) {
  const dispatch = useDispatch();

  return (
    <ButtonBox onClick={() => dispatch(changeTab(text))}>
      <div>{text}</div>
      <Icon name="DropdownCaret" />
    </ButtonBox>
  );
}
