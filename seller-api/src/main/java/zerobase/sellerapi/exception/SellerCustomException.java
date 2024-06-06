package zerobase.sellerapi.exception;


import zerobase.common.exception.CommonCustomException;
import zerobase.common.exception.CommonErrorCode;

public class SellerCustomException extends CommonCustomException {

  public SellerCustomException(SellerErrorCode errorCode) {
    super(errorCode);
  }

  public SellerCustomException(CommonErrorCode errorCode) {
    super(errorCode);
  }
}
