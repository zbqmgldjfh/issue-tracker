import styled from 'styled-components';

export const LoadingBox = styled.div`
  position: absolute;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  background: #fff;
  z-index: 9999999;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  & + button {
    display: none;
  }
`;

export const LoadingText = styled.div`
  font: 1.5rem 'Noto Sans KR';
  font-weight: 700;
  text-align: center;
  margin-top: 50px;
`;
