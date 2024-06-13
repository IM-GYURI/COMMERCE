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
public class CartItemRegisterDto {

  private String productKey;

  private Long count;

  public static CartItemRegisterDto fromEntity(CartItemEntity cartItemEntity) {
    return CartItemRegisterDto.builder()
        .productKey(cartItemEntity.getProductKey())
        .count(cartItemEntity.getCount())
        .build();
  }
}

