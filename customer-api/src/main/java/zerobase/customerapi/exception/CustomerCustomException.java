package zerobase.customerapi.exception;


import zerobase.common.exception.CommonCustomException;
import zerobase.common.exception.CommonErrorCode;

public class CustomerCustomException extends CommonCustomException {

  public CustomerCustomException(CustomerErrorCode errorCode) {
    super(errorCode);
  }

  public CustomerCustomException(CommonErrorCode errorCode) {
    super(errorCode);
  }
}
