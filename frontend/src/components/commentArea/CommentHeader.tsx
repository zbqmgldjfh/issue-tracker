/* eslint-disable no-confusing-arrow */
import React from 'react';
import { CommentHeaderBox, CommentHeaderButtons, CommentHeaderTabNav, Tab } from './CommentHeader.styled';

const tabList = ['Write', 'Preview'];

const createKey = (str: string, idx: number) => `value:${str}-idx:${idx}`;

export default function CommentHeader() {
  const tempState = 0;

  return (
    <CommentHeaderBox>
      <CommentHeaderTabNav>
        {tabList.map((tab: string, idx: number) =>
          idx === tempState ? (
            <Tab key={createKey(tab, idx)} active>
              {tab}
            </Tab>
          ) : (
            <Tab key={createKey(tab, idx)}>{tab}</Tab>
          ),
        )}
      </CommentHeaderTabNav>
      <CommentHeaderButtons />
    </CommentHeaderBox>
  );
}
