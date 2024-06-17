package zerobase.customerapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartEntityTest {

  /**
   * 장바구니 생성 테스트
   */
  @Test
  void testCartEntityBuilder() {
    // Given
    String customerKey = "customer123";

    // When
    CartEntity cartEntity = CartEntity.builder()
        .customerKey(customerKey)
        .build();

    // Then
    assertNotNull(cartEntity);
    assertEquals(customerKey, cartEntity.getCustomerKey());
    assertNotNull(cartEntity.getItems());
    assertEquals(0, cartEntity.getItems().size());
  }

  /**
   * 장바구니 담기 테스트
   */
  @Test
  void testCartEntityAddItem() {
    // Given
    String customerKey = "customer123";
    CartEntity cartEntity = CartEntity.builder()
        .customerKey(customerKey)
        .build();

    CartItemEntity item1 = CartItemEntity.builder()
        .productKey("P12345")
        .cartEntity(cartEntity)
        .price(1000L)
        .count(10L)
        .build();

    // When
    cartEntity.getItems().add(item1);

    // Then
    assertEquals(1, cartEntity.getItems().size());
    assertEquals(cartEntity, item1.getCart());
  }

  /**
   * 장바구니에서 해당 상품 삭제 테스트
   */
  @Test
  void testCartEntityRemoveItem() {
    // Given
    String customerKey = "customer123";
    CartEntity cartEntity = CartEntity.builder()
        .customerKey(customerKey)
        .build();

    CartItemEntity item1 = CartItemEntity.builder()
        .productKey("P12345")
        .cartEntity(cartEntity)
        .price(1000L)
        .count(10L)
        .build();

    cartEntity.getItems().add(item1);

    // When
    cartEntity.getItems().remove(item1);

    // Then
    assertEquals(0, cartEntity.getItems().size());
  }
}
