package zerobase.customerapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderItemEntityTest {

  @Test
  void testOrderItemEntityBuilder() {
    // Given
    String productKey = "P12345";
    Long price = 10000L;
    Long count = 2L;

    // When
    OrderItemEntity orderItemEntity = OrderItemEntity.builder()
        .productKey(productKey)
        .price(price)
        .count(count)
        .build();

    // Then
    assertNotNull(orderItemEntity);
    assertEquals(productKey, orderItemEntity.getProductKey());
    assertEquals(price, orderItemEntity.getPrice());
    assertEquals(count, orderItemEntity.getCount());
  }
}
