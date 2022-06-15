package codesquad.shine.issuetracker.exception.unchecked;

import codesquad.shine.issuetracker.exception.ErrorCode;

public class NotAvailableException extends BusinessException {

    public NotAvailableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
