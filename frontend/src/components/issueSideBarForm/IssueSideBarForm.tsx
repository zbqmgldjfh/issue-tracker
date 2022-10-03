import React, { useState } from 'react';
import { Icon } from 'src/components/common/icon/Icon';
import IssueModal from '../issueModal/IssueModal';
import { IssueSideBarFormBox, IssueSideBarFormContent, IssueSideBarHeader } from './IssueSideBarForm.styled';

type ModalInfoType = {
  title?: string;
  searchText?: string;
  bottomHeader?: string;
  searchResultArray?: any;
};

type IssueSideBarFormType = {
  title: string;
  contentStyle: string;
  defaultContent: string;
  hasModal?: boolean;
  modalInfo?: ModalInfoType;
};
export default function IssueSideBarForm({
  title,
  contentStyle = '',
  defaultContent = 'None yet',
  hasModal = true,
  modalInfo = undefined,
}: IssueSideBarFormType) {
  const [isModal, setIsModal] = useState(false);

  const handleClick = () => {
    setIsModal(!isModal);
  };

  return (
    <IssueSideBarFormBox>
      <IssueSideBarHeader>
        <div>{title}</div>
        {hasModal && (
          <div onClick={handleClick}>
            <Icon name="Setting" />
          </div>
        )}
      </IssueSideBarHeader>
      <IssueSideBarFormContent styles={contentStyle}>{defaultContent}</IssueSideBarFormContent>
      {modalInfo && isModal && (
        <IssueModal
          title={modalInfo.title || ''}
          setIsModal={setIsModal}
          searchText={modalInfo.searchText || ''}
          searchResultArray={modalInfo.searchResultArray || []}
        />
      )}
    </IssueSideBarFormBox>
  );
}
