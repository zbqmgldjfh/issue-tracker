import React from 'react';
import { SubmitButtonBox, SubmitButtonType } from './SubmitButton.styled';

export default function SubmitButton({ width, height }: SubmitButtonType) {
  return (
    <SubmitButtonBox width={width} height={height}>
      Submit new Issue
    </SubmitButtonBox>
  );
}
