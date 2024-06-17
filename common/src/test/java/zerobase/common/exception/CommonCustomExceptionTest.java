package zerobase.common.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;

import org.junit.jupiter.api.Test;

class CommonCustomExceptionTest {

  @Test
  public void testCommonCustomExceptionConstructor() {
    // Given
    ErrorCode errorCode = INVALID_REQUEST;
    String message = "올바르지 않은 요청입니다.";

    // When
    CommonCustomException exception = new CommonCustomException(errorCode, message);

    // Then
    assertEquals(errorCode, exception.getErrorCode());
    assertEquals(message, exception.getMessage());
  }
}