import React from 'react';
import DropdownCaret from 'src/assets/DropdownCaret.svg';
import Label from 'src/assets/Label.svg';
import Milestone from 'src/assets/Milestone.svg';
import IssueAttribute from '../common/issueAttribute/IssueAttribute';
import { HeaderBox, FilterBox, FilterButton, FilterInput, AttributesBox, NewIssueButton } from './IssuesHeader.styled';

export default function IssueHeader() {
  return (
    <HeaderBox>
      <FilterBox>
        <FilterButton>
          Filters
          <DropdownCaret />
        </FilterButton>
        <FilterInput />
      </FilterBox>
      <AttributesBox>
        <IssueAttribute SVG={Label} text={'Labels'} number={13} />
        <IssueAttribute SVG={Milestone} text={'Milestone'} number={3} />
      </AttributesBox>
      <NewIssueButton>New issue</NewIssueButton>
    </HeaderBox>
  );
}
