import React from 'react';
import Actions from 'src/assets/Actions.svg';
import Code from 'src/assets/Code.svg';
import Fork from 'src/assets/Fork.svg';
import Insights from 'src/assets/Insights.svg';
import Issues from 'src/assets/Issues.svg';
import Projects from 'src/assets/Projects.svg';
import PullRequests from 'src/assets/PullRequests.svg';
import Repository from 'src/assets/Repository.svg';
import Security from 'src/assets/Security.svg';
import Star from 'src/assets/Star.svg';
import Watch from 'src/assets/Watch.svg';
import Wiki from 'src/assets/Wiki.svg';
import ItemsNumber from '../itemsNumber/itemsNumber';
import NavArea from './navArea/NavArea';

import {
  RepositoryHeaderBox,
  TopArea,
  BottomArea,
  ContentArea,
  Status,
  TitleText,
  StatusText,
} from './RepositoryHeader.styled';

export default function RepositoryHeader() {
  return (
    <RepositoryHeaderBox>
      <TopArea>
        <ContentArea>
          <Repository />
          <TitleText>Howl</TitleText>/<TitleText>Issue-tracker</TitleText>
        </ContentArea>
        <ContentArea>
          <Status>
            <Watch />
            <StatusText>Watch</StatusText>
            <ItemsNumber number={0} />
          </Status>
          <Status>
            <Fork />
            <StatusText>Fork</StatusText>
            <ItemsNumber number={50} />
          </Status>
          <Status>
            <Star />
            <StatusText>Star</StatusText>
            <ItemsNumber number={0} />
          </Status>
        </ContentArea>
      </TopArea>
      <BottomArea>
        <NavArea text="Code" SVG={Code} />
        <NavArea text="Issues" SVG={Issues} />
        <NavArea text="Pull Requests" SVG={PullRequests} />
        <NavArea text="Actions" SVG={Actions} />
        <NavArea text="Projects" SVG={Projects} />
        <NavArea text="Wiki" SVG={Wiki} />
        <NavArea text="Security" SVG={Security} />
        <NavArea text="Insights" SVG={Insights} />
      </BottomArea>
    </RepositoryHeaderBox>
  );
}
