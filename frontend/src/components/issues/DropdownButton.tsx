import React from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { RootState } from 'src';
import { changeTab, deleteTab } from 'src/modules/clickedTab';
import { Icon } from 'src/components/common/icon/Icon';
import { ButtonBox, Button } from './DropdownButton.styled';
import DropdownMenu from './DropdownMenu';

type propsType = {
  modalWidth: string;
  text: string;
  makeItem: any;
};

export default function DropdownButton({ modalWidth, text, makeItem }: propsType) {
  const dispatch = useDispatch();
  const clickedTab = useSelector((state: RootState) => state.clickedTab);
  const btnEvent = (e: any) => {
    if (e.target.closest('[data-buttonname]').dataset.buttonname === clickedTab.clickedTab) dispatch(deleteTab());
    else dispatch(changeTab(text));
  };

  return (
    <ButtonBox>
      <Button onClick={btnEvent} data-buttonname={text}>
        {text}
        <Icon name="DropdownCaret" />
      </Button>
      <DropdownMenu modalWidth={modalWidth} detail={text} makeItem={makeItem} />
    </ButtonBox>
  );
}
