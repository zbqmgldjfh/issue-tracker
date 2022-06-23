import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from 'src/components/common/header/Header';
import RepositoryHeader from 'src/components/common/repositoryHeader/RepositoryHeader';

export default function HeaderPage() {
  return (
    <>
      <Header />
      <RepositoryHeader />
      <Outlet />
    </>
  );
}
