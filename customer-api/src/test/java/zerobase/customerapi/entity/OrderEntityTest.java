package zerobase.customerapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderEntityTest {

  /**
   * 주문 빌드 테스트
   */
  @Test
  void testOrderEntityBuilder() {
    // Given
    String customerKey = "C12345";
    Long totalPrice = 50000L;
    OrderItemEntity item1 = OrderItemEntity.builder()
        .productKey("P12345")
        .price(10000L)
        .count(2L)
        .build();
    OrderItemEntity item2 = OrderItemEntity.builder()
        .productKey("P67890")
        .price(15000L)
        .count(1L)
        .build();

    // When
    OrderEntity orderEntity = OrderEntity.builder()
        .customerKey(customerKey)
        .totalPrice(totalPrice)
        .items(Arrays.asList(item1, item2))
        .build();

    // Then
    assertNotNull(orderEntity);
    assertEquals(customerKey, orderEntity.getCustomerKey());
    assertEquals(totalPrice, orderEntity.getTotalPrice());
    assertEquals(2, orderEntity.getItems().size());
  }

  /**
   * 주문에 상품 담기 테스트
   */
  @Test
  void testOrderEntityAddItems() {
    // Given
    OrderEntity orderEntity = new OrderEntity();
    OrderItemEntity item1 = OrderItemEntity.builder()
        .productKey("P12345")
        .price(10000L)
        .count(2L)
        .build();
    OrderItemEntity item2 = OrderItemEntity.builder()
        .productKey("P67890")
        .price(15000L)
        .count(1L)
        .build();

    // When
    orderEntity.addItems(item1);
    orderEntity.addItems(item2);

    // Then
    assertEquals(2, orderEntity.getItems().size());
    assertEquals(item1, orderEntity.getItems().get(0));
    assertEquals(item2, orderEntity.getItems().get(1));
  }
}
