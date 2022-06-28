import React from 'react';
import { Icon } from '../common/icon/Icon';
import DropdownButton from './DropdownButton';
import { ToolbarBox, ToggleBox, OpenText, ClosedText, Toggle, DropdownList } from './IssuesToolbar.styled';

export default function IssuesToolbar() {
  const makeAuthorMenu = () => {
    return <></>;
  };

  return (
    <ToolbarBox>
      <ToggleBox>
        <input type="checkbox" />
        <Toggle>
          <Icon name="OpenIssues" />
          <OpenText>7 Open</OpenText>
        </Toggle>
        <Toggle>
          <Icon name="ClosedIssues" />
          <ClosedText>10 Closed</ClosedText>
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
