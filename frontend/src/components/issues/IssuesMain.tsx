import React from 'react';
import IssuesItem from './IssuesItem';
import { IssuesItems, IssuesMainBox } from './IssuesMain.styled';
import IssuesToolbar from './IssuesToolbar';

export default function IssuesMain() {
  return (
    <IssuesMainBox>
      <IssuesToolbar />
      <IssuesItems>
        <IssuesItem></IssuesItem>
        <IssuesItem></IssuesItem>
        <IssuesItem></IssuesItem>
        <IssuesItem></IssuesItem>
        <IssuesItem></IssuesItem>
        <IssuesItem></IssuesItem>
        <IssuesItem></IssuesItem>
        <IssuesItem></IssuesItem>
      </IssuesItems>
    </IssuesMainBox>
  );
}
