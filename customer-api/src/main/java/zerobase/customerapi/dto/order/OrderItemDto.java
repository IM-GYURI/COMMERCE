package zerobase.customerapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.customerapi.entity.OrderItemEntity;

/**
 * 주문 아이템 dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

  private String productKey;
  private Long price;
  private Long count;

  /**
   * OrderItemEntity -> OrderItemDto 변환
   *
   * @param orderItemEntity
   * @return
   */
  public static OrderItemDto fromEntity(OrderItemEntity orderItemEntity) {
    return OrderItemDto.builder()
        .productKey(orderItemEntity.getProductKey())
        .price(orderItemEntity.getPrice())
        .count(orderItemEntity.getCount())
        .build();
  }
}
