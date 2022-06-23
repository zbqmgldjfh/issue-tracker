import React from 'react';
import ClosedIssues from 'src/assets/ClosedIssues.svg';
import OpenIssues from 'src/assets/OpenIssues.svg';
import DropdownButton from '../common/dropdownButton/DropdownButton';
import { ToolbarBox, ToggleBox, OpenText, ClosedText, Toggle, DropdownList } from './IssuesToolbar.styled';

export default function IssuesToolbar() {
  return (
    <ToolbarBox>
      <ToggleBox>
        <input type="checkbox" />
        <Toggle>
          <OpenIssues />
          <OpenText>7 Open</OpenText>
        </Toggle>
        <Toggle>
          <ClosedIssues />
          <ClosedText>10 Closed</ClosedText>
        </Toggle>
      </ToggleBox>
      <DropdownList>
        <DropdownButton text="Author" />
        <DropdownButton text="Label" />
        <DropdownButton text="Projects" />
        <DropdownButton text="Milestones" />
        <DropdownButton text="Assignee" />
        <DropdownButton text="Sort" />
      </DropdownList>
    </ToolbarBox>
  );
}
