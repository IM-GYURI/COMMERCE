package zerobase.sellerapi.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.type.Role;
import zerobase.sellerapi.entity.SellerEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {

  private String sellerKey;
  private String name;
  private String email;
  private Role role;

  /**
   * SellerEntity -> SellerDto
   */
  public static SellerDto fromEntity(SellerEntity seller) {
    return SellerDto.builder()
        .sellerKey(seller.getSellerKey())
        .name(seller.getName())
        .email(seller.getEmail())
        .role(seller.getRole())
        .build();
  }
}
