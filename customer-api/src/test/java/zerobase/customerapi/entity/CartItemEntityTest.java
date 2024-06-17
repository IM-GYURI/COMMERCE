package zerobase.customerapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartItemEntityTest {

  /**
   * 장바구니 상품 빌드 테스트
   */
  @Test
  void testCartItemEntityBuilder() {
    // Given
    CartEntity cartEntity = CartEntity.builder()
        .customerKey("C12345")
        .build();

    String productKey = "P12345";
    Long price = 1000L;
    Long count = 2L;

    // When
    CartItemEntity cartItemEntity = CartItemEntity.builder()
        .cartEntity(cartEntity)
        .productKey(productKey)
        .price(price)
        .count(count)
        .build();

    // Then
    assertNotNull(cartItemEntity);
    assertEquals(cartEntity, cartItemEntity.getCart());
    assertEquals(productKey, cartItemEntity.getProductKey());
    assertEquals(price, cartItemEntity.getPrice());
    assertEquals(count, cartItemEntity.getCount());
  }

  /**
   * 장바구니 상품 수량 수정 테스트
   */
  @Test
  void testCartItemEntityUpdateCount() {
    // Given
    CartEntity cartEntity = CartEntity.builder()
        .customerKey("C12345")
        .build();

    CartItemEntity cartItemEntity = CartItemEntity.builder()
        .cartEntity(cartEntity)
        .productKey("P12345")
        .price(1000L)
        .count(2L)
        .build();

    Long newCount = 5L;

    // When
    cartItemEntity.updateCount(newCount);

    // Then
    assertEquals(newCount, cartItemEntity.getCount());
  }
}