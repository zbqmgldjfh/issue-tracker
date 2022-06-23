package codesquad.shine.issuetracker.exception.unchecked;

import codesquad.shine.issuetracker.exception.ErrorCode;

public class FileUploadFailedException extends BusinessException {
    public FileUploadFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
