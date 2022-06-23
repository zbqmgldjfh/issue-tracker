import React from 'react';
import { CommentBox, CommentBoxType } from './CommentArea.styled';

export default function CommentArea({ width, height }: CommentBoxType) {
  return <CommentBox width={width} height={height} placeholder="CommentBox" />;
}
