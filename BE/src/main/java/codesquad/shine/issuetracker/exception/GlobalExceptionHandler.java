package codesquad.shine.issuetracker.exception;

import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> tokenNotFoundExceptionHandler(NotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(errorCode.getMessage());
    }
}
