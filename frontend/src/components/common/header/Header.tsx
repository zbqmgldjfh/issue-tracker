import React from 'react';
import Bell from 'src/assets/Bell.svg';
import GithubIcon from 'src/assets/Github.svg';
import Plus from 'src/assets/Plus.svg';
import { HeaderBox, FlexBox, NavBar, SearchBox, Thumbnail } from './Header.styled';

export default function Header() {
  return (
    <>
      <HeaderBox>
        <FlexBox>
          <GithubIcon />
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
          <Bell />
          <Plus />
          <Thumbnail />
        </FlexBox>
      </HeaderBox>
    </>
  );
}
