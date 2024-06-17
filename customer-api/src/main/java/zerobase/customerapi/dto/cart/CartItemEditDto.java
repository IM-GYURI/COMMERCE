package zerobase.customerapi.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니에 담긴 상품의 수량을 수정하기 위한 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemEditDto {

  private String productKey;
  private Long newCount;
}
