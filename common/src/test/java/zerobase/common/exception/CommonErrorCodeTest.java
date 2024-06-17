package zerobase.common.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;
import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;
import static zerobase.common.exception.CommonErrorCode.PRODUCT_STOCK_NOT_ENOUGH;
import static zerobase.common.exception.CommonErrorCode.RESOURCE_NOT_FOUND;
import static zerobase.common.exception.CommonErrorCode.STOCK_NOT_MINUS;

import org.junit.jupiter.api.Test;

class CommonErrorCodeTest {

  @Test
  public void testProductNotFoundErrorCode() {
    // Given
    CommonErrorCode errorCode = PRODUCT_NOT_FOUND;

    // Then
    assertEquals(NOT_FOUND, errorCode.getStatus());
    assertEquals("상품을 찾을 수 없습니다,", errorCode.getMessage());
  }

  @Test
  public void testProductStockNotEnoughErrorCode() {
    // Given
    CommonErrorCode errorCode = PRODUCT_STOCK_NOT_ENOUGH;

    // Then
    assertEquals(BAD_REQUEST, errorCode.getStatus());
    assertEquals("상품의 재고가 부족합니다.", errorCode.getMessage());
  }

  @Test
  public void testStockNotMinusErrorCode() {
    // Given
    CommonErrorCode errorCode = STOCK_NOT_MINUS;

    // Then
    assertEquals(BAD_REQUEST, errorCode.getStatus());
    assertEquals("상품의 재고는 음수가 될 수 없습니다.", errorCode.getMessage());
  }

  @Test
  public void testResourceNotFoundErrorCode() {
    // Given
    CommonErrorCode errorCode = RESOURCE_NOT_FOUND;

    // Then
    assertEquals(NOT_FOUND, errorCode.getStatus());
    assertEquals("요청한 자원을 찾을 수 없습니다.", errorCode.getMessage());
  }

  @Test
  public void testInvalidRequestErrorCode() {
    // Given
    CommonErrorCode errorCode = INVALID_REQUEST;

    // Then
    assertEquals(BAD_REQUEST, errorCode.getStatus());
    assertEquals("올바르지 않은 요청입니다.", errorCode.getMessage());
  }
}