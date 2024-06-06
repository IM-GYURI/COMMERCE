package zerobase.sellerapi.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import zerobase.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum SellerErrorCode implements ErrorCode {
  // Seller
  SELLER_ALREADY_EXISTS(BAD_REQUEST, "이미 존재하는 판매자입니다."),
  SELLER_NOT_FOUND(NOT_FOUND, "판매자를 찾을 수 없습니다."),

  // Product
  PRODUCT_NOT_FOUND(NOT_FOUND, "상품을 찾을 수 없습니다,");
  
  private final HttpStatus status;
  private final String message;
}