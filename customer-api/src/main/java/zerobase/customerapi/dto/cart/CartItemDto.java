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

  private Long cartId;

  private String productKey;

  private Long price;

  private Long count;

  public static CartItemDto fromEntity(CartItemEntity cartItemEntity) {
    return CartItemDto.builder()
        .cartId(cartItemEntity.getCart().getId())
        .productKey(cartItemEntity.getProductKey())
        .price(cartItemEntity.getPrice())
        .count(cartItemEntity.getCount())
        .build();
  }
}
