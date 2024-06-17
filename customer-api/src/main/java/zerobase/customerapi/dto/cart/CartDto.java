package zerobase.customerapi.dto.cart;

import java.time.LocalDateTime;
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
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  /**
   * CartEntity -> CartDto 변환
   *
   * @param cart
   * @return
   */
  public static CartDto fromEntity(CartEntity cart) {
    return CartDto.builder()
        .customerKey(cart.getCustomerKey())
        .items(cart.getItems().stream()
            .map(CartItemDto::fromEntity)
            .collect(Collectors.toList()))
        .createdAt(cart.getCreatedAt())
        .modifiedAt(cart.getModifiedAt())
        .build();
  }
}
