import React from 'react';
import { createKey } from 'src/utils/utils';
import IssuesItem from './IssuesItem';
import { IssuesItems, IssuesMainBox } from './IssuesMain.styled';
import IssuesToolbar from './IssuesToolbar';

const mockData = [
  {
    title: '[FE] 이슈 페이지 컴포넌트 구현',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#25 opened 5 hours ago by hoi',
  },
  {
    title: '[FE] 이슈 아이템 컴포넌트 구현 ',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#24 opened 5 hours ago by hoi',
  },
  {
    title: '[FE] 이슈 아이템 데이터 생성',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#23 opened 5 hours ago by hoi',
  },
  {
    title: '[FE] CORS 에러..',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#22 opened 5 hours ago by hoi',
  },
  {
    title: '[FE] HTTPS에서 HTTP 어떻게?',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#21 opened 5 hours ago by hoi',
  },
  {
    title: '[FE] 이슈 페이지 컴포넌트 구현',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#20 opened 5 hours ago by hoi',
  },
  {
    title: '[FE] 이슈 페이지 컴포넌트 구현',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#19 opened 5 hours ago by hoi',
  },
  {
    title: '[FE] 이슈 페이지 컴포넌트 구현',
    avatarUrl: 'https://avatars.githubusercontent.com/u/87521172?s=40&v=4',
    author: '#18 opened 5 hours ago by hoi',
  },
];

export default function IssuesMain() {
  return (
    <IssuesMainBox>
      <IssuesToolbar />
      <IssuesItems>
        {mockData.map(({ title, author, avatarUrl }, idx) => (
          <IssuesItem key={createKey(title, idx)} title={title} author={author} avatarUrl={avatarUrl}></IssuesItem>
        ))}
      </IssuesItems>
    </IssuesMainBox>
  );
}
