type AllColorType = {
  primary: string;
  secondary: string;
  third: string;
  deep: string;
  green: string;
  lightGreen: string;
  complementary: string;
  box: string;
  focus: string;
  hover: string;
  basic: string;
  weak: string;
  bold: string;
  link: string;
  lightBlue: string;
  white: string;
  subText: string;
  header: string;
};

type BackgroundColorType = Pick<
  AllColorType,
  'basic' | 'primary' | 'secondary' | 'third' | 'deep' | 'green' | 'lightGreen' | 'complementary' | 'box'
>;

type borderColorType = Pick<AllColorType, 'primary' | 'secondary' | 'focus' | 'hover' | 'deep'>;

type ColorType = Pick<
  AllColorType,
  'basic' | 'primary' | 'weak' | 'bold' | 'hover' | 'link' | 'lightBlue' | 'white' | 'subText'
>;

type FillColorType = Pick<AllColorType, 'header' | 'green'>;

export type ThemeType = {
  background: BackgroundColorType;
  border: borderColorType;
  color: ColorType;
  fill: FillColorType;
};

export const darkMode = {
  background: {
    basic: '#22272E',
    primary: '#2d333b',
    secondary: '#22272e',
    third: '#444c56',
    deep: '#161b22',
    green: '#238636',
    lightGreen: '#46954a',
    complementary: '#eff0f6',
    box: '#373e47',
  },
  border: {
    primary: '#383e47',
    secondary: '#30363d',
    focus: '#57a6ff',
    hover: '#768390',
    deep: '#444c56',
  },
  color: {
    basic: '#adbac7',
    primary: '#cdd9e5',
    weak: '#6d7681',
    bold: '#768390',
    hover: '#9eb3b3',
    link: '#57a6ff',
    lightBlue: '#4c8cdb',
    white: '#fff',
    subText: '#a0a3bd',
  },
  fill: {
    header: '#8b949e',
    green: '#57ab5a',
  },
};

export const lightMode = {
  background: {
    basic: '#fff',
    primary: '#2d333b',
    secondary: '#d0d7de',
    third: '#F5F8FA',
    deep: '#F5F8FA',
    green: '#238636',
    lightGreen: '#46954a',
    complementary: '#22272E',
    box: '#24292E',
  },
  border: {
    primary: '#383e47',
    secondary: '#30363d',
    focus: '#57a6ff',
    hover: '#768390',
    deep: '#444c56',
  },
  color: {
    basic: '#000',
    primary: '#C8C9CB',
    weak: '#6d7681',
    bold: '#768390',
    hover: '#9eb3b3',
    link: '#57a6ff',
    lightBlue: '#4c8cdb',
    white: '#fff',
    subText: '#22272E',
  },
  fill: {
    header: '#8b949e',
    green: '#57ab5a',
  },
};
