import React from 'react';
import { Link } from 'react-router-dom';
import { Icon } from 'src/components/common/icon/Icon';
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
          <Icon name="Repository" />
          <Link to="/">
            <TitleText>Howl</TitleText>
          </Link>
          /
          <Link to="/">
            <TitleText>Issue-tracker</TitleText>
          </Link>
        </ContentArea>
        <ContentArea>
          <Status>
            <Icon name="Watch" />
            <StatusText>Watch</StatusText>
            <ItemsNumber number={0} />
          </Status>
          <Status>
            <Icon name="Fork" />
            <StatusText>Fork</StatusText>
            <ItemsNumber number={50} />
          </Status>
          <Status>
            <Icon name="Star" />
            <StatusText>Star</StatusText>
            <ItemsNumber number={0} />
          </Status>
        </ContentArea>
      </TopArea>
      <BottomArea>
        <NavArea content="Code" />
        <NavArea content="Issues" />
        <NavArea content="Pull Requests" />
        <NavArea content="Actions" />
        <NavArea content="Projects" />
        <NavArea content="Wiki" />
        <NavArea content="Security" />
        <NavArea content="Insights" />
      </BottomArea>
    </RepositoryHeaderBox>
  );
}
