package zerobase.sellerapi.dto.seller;

import java.time.LocalDateTime;
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
  private String email;
  private String name;
  private String phone;
  private String address;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Role role;

  /**
   * SellerEntity -> SellerDto
   */
  public static SellerDto fromEntity(SellerEntity seller) {
    return SellerDto.builder()
        .sellerKey(seller.getSellerKey())
        .email(seller.getEmail())
        .name(seller.getName())
        .phone(seller.getPhone())
        .address(seller.getAddress())
        .createdAt(seller.getCreatedAt())
        .modifiedAt(seller.getModifiedAt())
        .role(seller.getRole())
        .build();
  }
}
