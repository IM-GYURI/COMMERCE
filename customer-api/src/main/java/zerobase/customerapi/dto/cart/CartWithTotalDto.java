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
public class CartWithTotalDto {

  private String customerKey;

  private List<CartItemDto> items;

  private Long totalAmount;

  public static CartWithTotalDto fromEntity(CartEntity cart) {
    Long totalAmount = cart.getItems().stream()
        .mapToLong(item -> item.getPrice() * item.getCount())
        .sum();

    return CartWithTotalDto.builder()
        .customerKey(cart.getCustomerKey())
        .items(cart.getItems().stream()
            .map(CartItemDto::fromEntity)
            .collect(Collectors.toList()))
        .totalAmount(totalAmount)
        .build();
  }
}