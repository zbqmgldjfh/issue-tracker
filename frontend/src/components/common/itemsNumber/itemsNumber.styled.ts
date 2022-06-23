import styled from 'styled-components';

const ItemsNumberBox = styled.div`
  padding: 0 6px;
  min-width: 20px;
  font-size: 0.75rem;
  font-weight: 500;
  line-height: 19px;
  text-align: center;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.background.third};
  color: ${({ theme }) => theme.color.basic};
`;

export default ItemsNumberBox;
