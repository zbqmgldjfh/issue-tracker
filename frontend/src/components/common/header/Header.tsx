import React from 'react';
import { Icon } from '../icon/Icon';

import { HeaderBox, FlexBox, NavBar, SearchBox, Thumbnail } from './Header.styled';

export default function Header() {
  return (
    <>
      <HeaderBox>
        <FlexBox>
          <Icon name="GithubIcon" />
          <SearchBox>
            <input placeholder="Search or jump to..."></input>
          </SearchBox>
          <NavBar>
            <li>Pull request</li>
            <li>Issues</li>
            <li>Marketplace</li>
            <li>Explore</li>
          </NavBar>
        </FlexBox>
        <FlexBox>
          <Icon name="Bell" />
          <Icon name="Plus" />
          <Thumbnail />
        </FlexBox>
      </HeaderBox>
    </>
  );
}
