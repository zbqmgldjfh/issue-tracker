package codesquad.shine.issuetracker.exception.unchecked;

import codesquad.shine.issuetracker.exception.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
