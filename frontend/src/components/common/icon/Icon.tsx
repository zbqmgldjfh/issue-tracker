import React from 'react';
import Actions from 'src/assets/Actions.svg';
import Code from 'src/assets/Code.svg';
import Insights from 'src/assets/Insights.svg';
import Issues from 'src/assets/Issues.svg';
import Projects from 'src/assets/Projects.svg';
import PullRequests from 'src/assets/PullRequests.svg';
import Security from 'src/assets/Security.svg';
import Wiki from 'src/assets/Wiki.svg';
import Repository from 'src/assets/Repository.svg';
import Watch from 'src/assets/Watch.svg';
import Fork from 'src/assets/Fork.svg';
import Star from 'src/assets/Star.svg';
import DropdownCaret from 'src/assets/DropdownCaret.svg';
import Bell from 'src/assets/Bell.svg';
import GithubIcon from 'src/assets/Github.svg';
import Plus from 'src/assets/Plus.svg';
import CloseIcon from 'src/assets/Close.svg';
import Labels from 'src/assets/Label.svg';
import Milestone from 'src/assets/Milestone.svg';
import OpenIssues from 'src/assets/OpenIssues.svg';
import ReviewIcon from 'src/assets/Review.svg';
import ClosedIssues from 'src/assets/ClosedIssues.svg';
import Setting from 'src/assets/Setting.svg';

export type IconPropType = keyof typeof iconComponentGroup;

type IconType = {
  name: IconPropType;
  onClick?: () => void;
};

const iconComponentGroup = {
  Actions,
  CloseIcon,
  Code,
  Fork,
  Insights,
  Issues,
  Projects,
  'Pull Requests': PullRequests,
  Repository,
  Security,
  Bell,
  GithubIcon,
  Plus,
  Star,
  Watch,
  Wiki,
  DropdownCaret,
  Labels,
  Milestone,
  OpenIssues,
  ReviewIcon,
  ClosedIssues,
  Setting,
};

export function Icon({ name, onClick }: IconType) {
  const SelectedIcon = iconComponentGroup[name];
  return <SelectedIcon onClick={onClick} />;
}
