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
  POINT_INVALID(BAD_REQUEST, "충전하는 포인트는 0원 이하일 수 없습니다."),
  POINT_NOT_ENOUGH(BAD_REQUEST, "포인트가 부족합니다."),

  // Cart
  CART_NOT_FOUND(NOT_FOUND, "해당 고객의 장바구니가 존재하지 않습니다."),
  CART_ITEM_NOT_FOUND(NOT_FOUND, "장바구니에 해당 상품이 존재하지 않습니다."),
  CART_INVALID_PRODUCT_COUNT(BAD_REQUEST, "장바구니 상품의 수량은 음수가 될 수 없습니다."),
  CART_ALREADY_EXISTS(BAD_REQUEST, "해당 고객의 장바구니가 이미 존재합니다."),

  // Order
  ORDER_NOT_EXISTS(BAD_REQUEST, "해당 고객의 주문 내역이 존재하지 않습니다.");

  private final HttpStatus status;
  private final String message;
}