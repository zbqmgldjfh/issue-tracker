import React from 'react';
import { ThemeButtonBox } from './ThemeButton.styled';

type ThemeButtonType = {
  themeMode: string;
  changeTheme: () => void;
};

export default function ThemeButton({ themeMode, changeTheme }: ThemeButtonType) {
  return <ThemeButtonBox onClick={changeTheme} mode={themeMode} />;
}
