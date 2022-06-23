import React from 'react';
import { Bottom, Header, IssueModalBox, Main, SearchBarBox } from './IssueModal.styled';

type ModalType = {
  title: string;
  searchText: string;
  searchResultArray: any;
};

export default function IssueModal({ title, searchText, searchResultArray }: ModalType) {
  // modal에 표시할 상태가 추가되면 searchResultArray에 담아서 넘어 올 것
  return (
    <IssueModalBox>
      <Header>{title}</Header>
      <Main>
        <SearchBarBox placeholder={searchText} />
      </Main>
      <Bottom>
        {searchResultArray.length > 0 &&
          searchResultArray.map((ResultElement: any, idx: any) => <ResultElement key={`${idx}-${Math.random()}`} />)}
      </Bottom>
    </IssueModalBox>
  );
}
