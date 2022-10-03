import React from 'react';
import { Link } from 'react-router-dom';
import { Icon } from 'src/components/common/icon/Icon';
import IssueAttribute from '../common/issueAttribute/IssueAttribute';
import { HeaderBox, FilterBox, FilterButton, FilterInput, AttributesBox, NewIssueButton } from './IssuesHeader.styled';

export default function IssueHeader() {
  return (
    <HeaderBox>
      <FilterBox>
        <FilterButton>
          Filters
          <Icon name="DropdownCaret" />
        </FilterButton>
        <FilterInput />
      </FilterBox>
      <AttributesBox>
        <IssueAttribute text={'Labels'} number={13} />
        <IssueAttribute text={'Milestone'} number={3} />
      </AttributesBox>
      <Link to="/issues-new">
        <NewIssueButton>New issue</NewIssueButton>
      </Link>
    </HeaderBox>
  );
}
