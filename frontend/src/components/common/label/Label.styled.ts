import styled from 'styled-components';
import { LabelType } from './Label';

type StyledLabelType = Pick<LabelType, 'fontColor' | 'backgroundColor'>;

const StyledLabel = styled.span<StyledLabelType>`
  color: ${({ fontColor }) => fontColor};
  background-color: ${({ backgroundColor }) => backgroundColor};
  padding: 5px 10px;
  border-radius: 10px;
`;

export default StyledLabel;
