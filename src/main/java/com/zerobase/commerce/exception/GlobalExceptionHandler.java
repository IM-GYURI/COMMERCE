package com.zerobase.commerce.exception;

import static com.zerobase.commerce.exception.ErrorCode.INVALID_REQUEST;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException e) {
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
}
