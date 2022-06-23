package codesquad.shine.issuetracker.exception.unchecked;

import codesquad.shine.issuetracker.exception.ErrorCode;

public class CommentFormatException extends BusinessException {
    public CommentFormatException(ErrorCode errorCode) {
        super(errorCode);
    }
}
