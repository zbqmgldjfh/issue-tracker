import React from 'react';
import StyledLabel from './Label.styled';

export type LabelType = {
  fontColor: string;
  backgroundColor: string;
  text: string;
};

export default function Label({ fontColor, backgroundColor, text }: LabelType) {
  return (
    <StyledLabel fontColor={fontColor} backgroundColor={backgroundColor}>
      {text}
    </StyledLabel>
  );
}
