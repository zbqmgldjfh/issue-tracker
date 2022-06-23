import React from 'react';
import IssueHeader from 'src/components/issues/IssuesHeader';
import IssuesMain from 'src/components/issues/IssuesMain';
import IssuesPageBox from './IssuesPage.styled';

export default function IssuesPage() {
  return (
    <IssuesPageBox>
      <IssueHeader />
      <IssuesMain />
    </IssuesPageBox>
  );
}
