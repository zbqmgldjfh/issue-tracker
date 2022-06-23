import styled from 'styled-components';

const FileAttachButtonBox = styled.input.attrs({ type: 'file', accept: '.gif, .jpg, .png, .jpeg', multiple: true })`
  size: 0.875em;
`;

export default FileAttachButtonBox;
