import styled from 'styled-components';

type ThemeButtonBoxType = {
  mode: string;
};

const setColor = (mode: string) => {
  if (mode === 'dark') {
    return '0px 5px 10px white, 0px 2px 4px white';
  }
  return '0 5px 10px black, 0 2px 4px black';
};

// eslint-disable-next-line import/prefer-default-export
export const ThemeButtonBox = styled.button<ThemeButtonBoxType>`
  position: fixed;
  z-index: 999999;
  bottom: 4%;
  right: 3%;

  background-color: ${({ theme }) => theme.background.basic};
  color: ${({ theme }) => theme.color.basic};
  border: ${({ theme }) => theme.border.primary};
  font-size: 20px;

  display: flex;
  justify-content: center;
  align-items: center;
  width: 96px;
  height: 48px;
  border-radius: 30px;
  box-shadow: ${({ mode }) => setColor(mode)};
`;
