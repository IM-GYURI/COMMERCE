package zerobase.customerapi.dto.cart;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.customerapi.entity.CartItemEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

  private String productKey;

  private Long price;

  private Long count;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  /**
   * CartItemEntity -> CartItemDto 변환
   *
   * @param cartItemEntity
   * @return
   */
  public static CartItemDto fromEntity(CartItemEntity cartItemEntity) {
    return CartItemDto.builder()
        .productKey(cartItemEntity.getProductKey())
        .price(cartItemEntity.getPrice())
        .count(cartItemEntity.getCount())
        .createdAt(cartItemEntity.getCreatedAt())
        .modifiedAt(cartItemEntity.getModifiedAt())
        .build();
  }
}
