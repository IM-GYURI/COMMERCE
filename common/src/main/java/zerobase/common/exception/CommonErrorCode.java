package zerobase.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
  // Product
  PRODUCT_NOT_FOUND(NOT_FOUND, "상품을 찾을 수 없습니다,"),
  PRODUCT_STOCK_NOT_ENOUGH(BAD_REQUEST, "상품의 재고가 부족합니다."),
  STOCK_NOT_MINUS(BAD_REQUEST, "상품의 재고는 음수가 될 수 없습니다."),

  // Global
  RESOURCE_NOT_FOUND(NOT_FOUND, "요청한 자원을 찾을 수 없습니다."),
  INVALID_REQUEST(BAD_REQUEST, "올바르지 않은 요청입니다.");

  private final HttpStatus status;
  private final String message;
}
