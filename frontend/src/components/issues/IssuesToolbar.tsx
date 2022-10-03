import React from 'react';
import { Icon } from 'src/components/common/icon/Icon';
import DropdownButton from './DropdownButton';
import { ToolbarBox, ToggleBox, OpenText, ClosedText, Toggle, DropdownList } from './IssuesToolbar.styled';

type IssuesCountType = {
  openCount: number;
  closedCount: number;
};

type IssuesToolbarType = {
  issuesCount: IssuesCountType;
};

export default function IssuesToolbar({ issuesCount }: IssuesToolbarType) {
  const makeAuthorMenu = () => {
    return <></>;
  };
  const { openCount, closedCount } = issuesCount;

  return (
    <ToolbarBox>
      <ToggleBox>
        <input type="checkbox" />
        <Toggle>
          <Icon name="OpenIssues" />
          <OpenText>{openCount} Open</OpenText>
        </Toggle>
        <Toggle>
          <Icon name="ClosedIssues" />
          <ClosedText>{closedCount} Closed</ClosedText>
        </Toggle>
      </ToggleBox>
      <DropdownList>
        <DropdownButton modalWidth="300px" text="Author" makeItem={makeAuthorMenu} />
        <DropdownButton modalWidth="300px" text="Label" makeItem={makeAuthorMenu} />
        <DropdownButton modalWidth="300px" text="Projects" makeItem={makeAuthorMenu} />
        <DropdownButton modalWidth="300px" text="Milestones" makeItem={makeAuthorMenu} />
        <DropdownButton modalWidth="300px" text="Assignee" makeItem={makeAuthorMenu} />
        <DropdownButton modalWidth="300px" text="Sort" makeItem={makeAuthorMenu} />
      </DropdownList>
    </ToolbarBox>
  );
}
