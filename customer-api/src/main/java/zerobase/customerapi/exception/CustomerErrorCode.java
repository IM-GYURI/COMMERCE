package zerobase.customerapi.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import zerobase.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum CustomerErrorCode implements ErrorCode {
  // Customer
  CUSTOMER_ALREADY_EXISTS(BAD_REQUEST, "이미 존재하는 고객입니다."),
  CUSTOMER_NOT_FOUND(NOT_FOUND, "고객을 찾을 수 없습니다."),

  // Cart
  CART_NOT_FOUND(NOT_FOUND, "해당 고객의 장바구니가 존재하지 않습니다."),
  CART_ALREADY_EXISTS(BAD_REQUEST, "해당 고객의 장바구니가 이미 존재합니다.");

  private final HttpStatus status;
  private final String message;
}