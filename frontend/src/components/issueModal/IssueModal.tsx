import React, { useRef } from 'react';
import { useOutsideClick } from 'src/hooks/useOutsideClick';
import { Bottom, Header, IssueModalBox, Main, SearchBarBox } from './IssueModal.styled';

type ModalType = {
  title: string;
  searchText: string;
  searchResultArray: any;
  setIsModal: React.Dispatch<React.SetStateAction<boolean>>;
};

export default function IssueModal({ setIsModal, title, searchText, searchResultArray }: ModalType) {
  // modal에 표시할 상태가 추가되면 searchResultArray에 담아서 넘어 올 것
  const modalRef: React.MutableRefObject<HTMLElement[] | null[]> = useRef([]);
  const handleOutsideClick = () => {
    setIsModal(false);
  };

  useOutsideClick(modalRef, handleOutsideClick);

  return (
    <IssueModalBox ref={(el) => modalRef && (modalRef.current[0] = el)}>
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
