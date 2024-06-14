package zerobase.customerapi.dto.cart;

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
        .build();
  }
}
