package zerobase.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // Customer
  CUSTOMER_ALREADY_EXISTS(BAD_REQUEST, "이미 존재하는 고객입니다."),
  CUSTOMER_NOT_FOUND(NOT_FOUND, "고객을 찾을 수 없습니다."),

  // Seller
  SELLER_ALREADY_EXISTS(BAD_REQUEST, "이미 존재하는 판매자입니다."),
  SELLER_NOT_FOUND(NOT_FOUND, "판매자를 찾을 수 없습니다."),

  // Product
  PRODUCT_NOT_FOUND(NOT_FOUND, "상품을 찾을 수 없습니다,"),

  // Global
  RESOURCE_NOT_FOUND(NOT_FOUND, "요청한 자원을 찾을 수 없습니다."),
  INVALID_REQUEST(BAD_REQUEST, "올바르지 않은 요청입니다.");

  private final HttpStatus status;
  private final String message;
}
