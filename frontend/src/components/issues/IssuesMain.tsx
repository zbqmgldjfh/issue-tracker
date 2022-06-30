import React, { useEffect, useState } from 'react';
import { ISSUES_API, SERVER_URL } from 'src/constants/constant';
import { createKey, fetchData } from 'src/utils/utils';
import IssuesItem from './IssuesItem';
import { IssuesItems, IssuesMainBox } from './IssuesMain.styled';
import IssuesToolbar from './IssuesToolbar';

const ISSUES_API_URL = SERVER_URL + ISSUES_API;

type IssueDataType = {
  title: string;
  author: any;
};

export default function IssuesMain() {
  const [issuesCount, setIssuesCount] = useState({
    openCount: 0,
    closedCount: 0,
  });
  const [issuesData, setIssuesData] = useState([]);

  useEffect(() => {
    const getData = async () => {
      const { openCount, closedCount, issues } = await fetchData(ISSUES_API_URL);
      const { content } = issues;

      const issuesData = content.map(({ title, author }: IssueDataType) => {
        const { userName, avatarUrl } = author;
        return { title, author: userName, avatarUrl };
      });

      setIssuesCount({
        openCount,
        closedCount,
      });
      setIssuesData(issuesData);
    };

    getData();
  }, []);

  console.log(issuesData);

  return (
    <IssuesMainBox>
      <IssuesToolbar issuesCount={issuesCount} />
      <IssuesItems>
        {issuesData.map(({ title, author, avatarUrl }, idx) => (
          <IssuesItem key={createKey(title, idx)} title={title} author={author} avatarUrl={avatarUrl}></IssuesItem>
        ))}
      </IssuesItems>
    </IssuesMainBox>
  );
}
