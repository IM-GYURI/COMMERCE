package zerobase.common.exception;

import lombok.Getter;

@Getter
public class CommonCustomException extends RuntimeException implements CustomException {

  private final ErrorCode errorCode;
  private final String message;

  public CommonCustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.message = errorCode.getMessage();
  }

  public CommonCustomException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
  }
}
