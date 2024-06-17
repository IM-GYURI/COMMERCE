package zerobase.customerapi.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.customerapi.entity.OrderEntity;

/**
 * 주문을 위한 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

  private String customerKey;
  private List<OrderItemDto> items;
  private Long totalPrice;
  private LocalDateTime createdAt;

  /**
   * OrderEntity -> OrderDto 변환
   *
   * @param orderEntity
   * @return
   */
  public static OrderDto fromEntity(OrderEntity orderEntity) {
    return OrderDto.builder()
        .customerKey(orderEntity.getCustomerKey())
        .items(orderEntity.getItems().stream()
            .map(OrderItemDto::fromEntity)
            .collect(Collectors.toList()))
        .totalPrice(orderEntity.getTotalPrice())
        .createdAt(orderEntity.getCreatedAt())
        .build();
  }
}
