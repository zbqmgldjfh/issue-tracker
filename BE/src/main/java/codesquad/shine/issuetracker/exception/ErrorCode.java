package codesquad.shine.issuetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰 정보가 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    INVALID_USER(HttpStatus.UNAUTHORIZED, "해당 회원은 권한이 없습니다."),
    LABEL_NOT_FOUND(HttpStatus.NOT_FOUND, "Label 정보가 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment 정보가 존재하지 않습니다."),
    ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "Issue 정보가 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User 정보가 존재하지 않습니다."),
    MILESTONE_NOT_FOUND(HttpStatus.NOT_FOUND, "MileStone 정보가 존재하지 않습니다."),
    FILE_NAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "File의 이름이 존재하지 않습니다."),
    FILE_CANT_UPLOAD(HttpStatus.SERVICE_UNAVAILABLE, "File을 업로드 할 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
