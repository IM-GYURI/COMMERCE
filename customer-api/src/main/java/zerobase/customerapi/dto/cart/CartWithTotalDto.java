package zerobase.customerapi.dto.cart;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.customerapi.entity.CartEntity;

/**
 * 장바구니 조회 시 전체 가격을 함께 보여주기 위한 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartWithTotalDto {

  private String customerKey;

  private List<CartItemDto> items;

  private Long totalAmount;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  /**
   * CartEntity -> CartWithTotalDto 변환
   *
   * @param cart
   * @return
   */
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
        .createdAt(cart.getCreatedAt())
        .modifiedAt(cart.getModifiedAt())
        .build();
  }
}