package zerobase.customerapi.dto.cart;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.customerapi.entity.CartEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

  private String customerKey;

  private List<CartItemDto> items;

  public static CartDto fromEntity(CartEntity cart) {
    return CartDto.builder()
        .customerKey(cart.getCustomerKey())
        .items(cart.getItems().stream()
            .map(CartItemDto::fromEntity)
            .collect(Collectors.toList()))
        .build();
  }
}
