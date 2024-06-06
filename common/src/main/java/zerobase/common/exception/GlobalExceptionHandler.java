package zerobase.common.exception;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;

import java.security.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CommonCustomException.class)
  public ResponseEntity<?> handleCustomException(CommonCustomException e) {
    return toResponse(e.getErrorCode(), e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidException(
      MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getAllErrors().get(0)
        .getDefaultMessage();

    return toResponse(INVALID_REQUEST, message);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrityViolationException(
      DataIntegrityViolationException e) {
    return toResponse(INVALID_REQUEST, INVALID_REQUEST.getMessage());
  }

  private static ResponseEntity<ErrorResponse> toResponse(
      ErrorCode errorCode, String message) {
    return ResponseEntity.status(errorCode.getStatus())
        .body(new ErrorResponse(errorCode, message));
  }

  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<?> handleSignatureException(SignatureException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
  }
}
