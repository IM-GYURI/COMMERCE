package zerobase.common.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;

import java.security.SignatureException;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  public void testHandleCustomException() {
    // Given
    CommonCustomException exception = new CommonCustomException(INVALID_REQUEST,
        "올바르지 않은 요청입니다.");

    // When
    ResponseEntity<?> response = handler.handleCustomException(exception);
    ErrorResponse errorResponse = (ErrorResponse) response.getBody();
    String errorMessage = Objects.requireNonNull(errorResponse).message();

    // Then
    assertEquals(BAD_REQUEST, response.getStatusCode());
    assertEquals("올바르지 않은 요청입니다.", errorMessage);
  }

  @Test
  public void testHandleMethodArgumentNotValidException() {
    // Given
    String errorMessage = "올바르지 않은 요청입니다.";
    FieldError fieldError = new FieldError("objectName", "fieldName", errorMessage);
    BindingResult bindingResult = new BindException(new Object(), "target");
    bindingResult.addError(fieldError);
    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
        null,
        bindingResult
    );

    // When
    ResponseEntity<?> response = handler.handleValidException(exception);
    ErrorResponse errorResponse = (ErrorResponse) response.getBody();
    String errorMessage2 = Objects.requireNonNull(errorResponse).message();

    // Then
    assertEquals(BAD_REQUEST, response.getStatusCode());
    assertEquals(INVALID_REQUEST.getMessage(), errorMessage2);
  }

  @Test
  public void testHandleDataIntegrityViolationException() {
    // Given
    DataIntegrityViolationException exception = new DataIntegrityViolationException(
        "올바르지 않은 요청입니다.");

    // When
    ResponseEntity<?> response = handler.handleDataIntegrityViolationException(exception);
    ErrorResponse errorResponse = (ErrorResponse) response.getBody();
    String errorMessage = Objects.requireNonNull(errorResponse).message();

    // Then
    assertEquals(BAD_REQUEST, response.getStatusCode());
    assertEquals("올바르지 않은 요청입니다.", errorMessage);
  }

  @Test
  public void testHandleSignatureException() {
    // Given
    SignatureException exception = new SignatureException("토큰이 유효하지 않습니다.");

    // When
    ResponseEntity<?> response = handler.handleSignatureException(exception);

    // Then
    assertEquals(UNAUTHORIZED, response.getStatusCode());
    assertEquals("토큰이 유효하지 않습니다.", response.getBody());
  }
}