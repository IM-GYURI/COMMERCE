package zerobase.customerapi.dto.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRegisterDto {

  @NotBlank
  private String customerKey;

  private String productKey;

  private Long price;

  private Long count;

}
