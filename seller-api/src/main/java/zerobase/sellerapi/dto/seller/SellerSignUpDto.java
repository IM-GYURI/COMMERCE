package zerobase.sellerapi.dto.seller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.sellerapi.entity.SellerEntity;

/**
 * 회원가입 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerSignUpDto {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String name;

  @NotBlank
  private String phone;

  @NotBlank
  private String address;

  /**
   * 판매자 키와 비밀번호를 받아 SellerEntity 빌드
   */
  public SellerEntity toEntity(String sellerKey, String password) {
    return SellerEntity.builder()
        .sellerKey(sellerKey)
        .email(email)
        .password(password)
        .name(name)
        .phone(phone)
        .address(address)
        .build();
  }
}
