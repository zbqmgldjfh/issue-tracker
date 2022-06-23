import React from 'react';
import CommentArea from 'src/components/commentArea/CommentArea';
import CommentHeader from 'src/components/commentArea/CommentHeader';
import FileAttachButton from 'src/components/fileAttachButton/FileAttachButton';
import SubmitButton from 'src/components/submitButton/SubmitButton';
import Title from 'src/components/title/Title';
import { CommentAreaBox, CommentAreaFooter, CommentAreaMain, CreateIssueBox } from './CreateIssue.styled';

export default function CreateIssue() {
  return (
    <CreateIssueBox width="838px" height="360px">
      <CommentAreaBox>
        <Title width="100%" height="30px" />
        <CommentAreaMain>
          <CommentHeader />
          <CommentArea width="100%" height="200px" />
        </CommentAreaMain>
        <CommentAreaFooter>
          <FileAttachButton></FileAttachButton>
          <SubmitButton width="148px" height="30px" />
        </CommentAreaFooter>
      </CommentAreaBox>
    </CreateIssueBox>
  );
}
