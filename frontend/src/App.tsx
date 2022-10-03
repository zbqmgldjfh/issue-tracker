import React, { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyle from './common/GlobalStyle';
import { darkMode, lightMode } from './common/Theme';
import ThemeButton from './components/themeButton/ThemeButton';
import AddIssuePage from './pages/createIssuePage/CreateIssuePage';
import HeaderPage from './pages/header/HeaderPage';
import IssuesPage from './pages/issues/IssuesPage';
import LoadingPage from './pages/loading/LoadingPage';
import LoginPage from './pages/login/LoginPage';

export default function App() {
  const [themeMode, setThemeMode] = useState('dark');
  const theme = themeMode === 'dark' ? darkMode : lightMode;

  const changethemeMode = () => {
    setThemeMode((cur: string) => (cur === 'dark' ? 'light' : 'dark'));
  };

  return (
    <>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/loading" element={<LoadingPage />} />
            <Route path="/" element={<HeaderPage />}>
              <Route path="/" element={<IssuesPage />} />
              <Route path="/issues-new" element={<AddIssuePage />} />
            </Route>
          </Routes>
          <ThemeButton themeMode={themeMode} changeTheme={changethemeMode} />
        </BrowserRouter>
      </ThemeProvider>
    </>
  );
}
