import React from 'react';
import { useDispatch } from 'react-redux';
import { shallowEqual, useSelector } from 'react-redux';
import { RootState } from 'src';
import { deleteTab } from 'src/modules/clickedTab';
import { Icon } from 'src/components/common/icon/Icon';
import { MenuBox, Title, Main, Header, Input } from './DropdownMenu.styled';

type PropsType = {
  modalWidth: string;
  detail: string;
  makeItem: any;
};

export default function DropdownMenu({ modalWidth, detail, makeItem }: PropsType) {
  const clickedTab = useSelector((state: RootState) => state.clickedTab, shallowEqual);
  const dispatch = useDispatch();

  return (
    <MenuBox isClicked={clickedTab.clickedTab === detail} width={modalWidth}>
      <Header>
        <Title>Filter by {detail}</Title>
        <Icon name="CloseIcon" onClick={() => dispatch(deleteTab())} />
      </Header>
      <br />
      <Main>
        <Input placeholder="Filter users"></Input>
        <br />
        {makeItem()}
      </Main>
    </MenuBox>
  );
}
