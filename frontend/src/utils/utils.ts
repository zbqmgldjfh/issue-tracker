import { css } from 'styled-components';

export const flexLayoutMixin = (direction = 'row', justify = 'flex-start', align = 'stretch') => css`
  display: flex;
  flex-direction: ${direction};
  justify-content: ${justify};
  align-items: ${align};
`;

const getErrorMessage = (error: unknown) => {
  if (error instanceof Error) return error.message;
  return String(error);
};

export const fetchData = async (url: string) => {
  try {
    const response: Response = await fetch(url);

    if (response.ok) {
      return response.json();
    }
    throw new Error(response.statusText);
  } catch (error) {
    reportError({ message: getErrorMessage(error) });
  }
};

export const setBackgroundColor = (background: string) => `background-color:${background}`;
