/* eslint-disable array-element-newline */
import React from 'react';
import CreateIssue from 'src/components/createIssue/CreateIssue';
import IssueSideBarForm from 'src/components/issueSideBarForm/IssueSideBarForm';

import { CreateIssuePageBox, SideBar } from './CreateIssuePage.styled';

const createKey = (value: string, key: number) => `value:${value}-key:${key}`;

const sideBarInfo = [
  {
    title: 'Assignees',
    defaultContent: 'No one-assign yourself',
    modalInfo: {
      title: 'Assign up to 10 people to this issue',
      searchText: 'Find a user',
      bottomHeader: 'Nothing to show',
    },
  },
  {
    title: 'Labels',
    defaultContent: 'None yet',
    style: 'display:flex; flex-direction:row',
    modalInfo: {
      title: 'Apply labels to this issue',
      searchText: 'Find a labels',
      bottomHeader: 'Nothing to show',
    },
  },
  {
    title: 'Projects',
    defaultContent: 'None yet',
    modalInfo: {
      title: 'Projects',
      searchText: 'Filter projects',
    },
  },
  {
    title: 'Milestone',
    defaultContent: 'No milestone',
    modalInfo: {
      title: 'Set milestone',
      searchText: 'Filter milestones',
    },
  },
  {
    title: 'Development',
    defaultContent: 'Shows branches and pull requests linked to this issue.    ',
    hasModal: false,
  },
  {
    title: 'Helpful resources',
    defaultContent: 'GitHub Community Guidelines',
    hasModal: false,
  },
];

export default function AddIssuePage() {
  return (
    <CreateIssuePageBox>
      <CreateIssue />
      <SideBar>
        {sideBarInfo.map(({ title, defaultContent, style, hasModal, modalInfo }, idx) => (
          <IssueSideBarForm
            key={createKey(title, idx)}
            title={title}
            defaultContent={defaultContent}
            contentStyle={style || ''}
            hasModal={hasModal}
            modalInfo={modalInfo}
          />
        ))}
      </SideBar>
    </CreateIssuePageBox>
  );
}
