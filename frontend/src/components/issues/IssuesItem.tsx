import React from 'react';
import { Icon } from 'src/components/common/icon/Icon';
import { Assignee, FlexBox, IssuesItemBox, ItemMain, OpenedBy, Review, ReviewNum, Title } from './IssuesItem.styled';

export default function IssuesItem() {
  return (
    <IssuesItemBox>
      <input type="checkbox" />
      <Icon name="OpenIssues" />
      <ItemMain>
        <Title>[FE] 이슈 페이지 컴포넌트 구현</Title>
        <OpenedBy>#25 opened 5 hours ago by owl</OpenedBy>
      </ItemMain>
      <FlexBox />
      <Assignee>
        <img src="https://avatars.githubusercontent.com/u/83114018?s=40&amp;v=4" />
      </Assignee>
      <Review>
        <Icon name="ReviewIcon" />
        <ReviewNum>13</ReviewNum>
      </Review>
    </IssuesItemBox>
  );
}
