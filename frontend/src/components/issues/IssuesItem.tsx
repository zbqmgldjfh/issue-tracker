import React from 'react';
import { Icon } from 'src/components/common/icon/Icon';
import { Assignee, FlexBox, IssuesItemBox, ItemMain, OpenedBy, Review, ReviewNum, Title } from './IssuesItem.styled';

type IssueItemType = {
  title: string;
  author: string;
  avatarUrl: string;
};

export default function IssuesItem({ title, author, avatarUrl }: IssueItemType) {
  return (
    <IssuesItemBox>
      <input type="checkbox" />
      <Icon name="OpenIssues" />
      <ItemMain>
        <Title>{title}</Title>
        <OpenedBy>{author}</OpenedBy>
      </ItemMain>
      <FlexBox />
      <Assignee>
        <img src={avatarUrl} />
      </Assignee>
      <Review>
        <Icon name="ReviewIcon" />
        <ReviewNum>13</ReviewNum>
      </Review>
    </IssuesItemBox>
  );
}
