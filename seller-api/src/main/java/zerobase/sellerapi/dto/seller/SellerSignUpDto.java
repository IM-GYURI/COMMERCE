package zerobase.sellerapi.dto.seller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

  @NotBlank(message = "이메일은 필수 입력값 입니다.")
  @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
  @Email(message = "이메일 형식에 맞지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자로, 영문 대소문자, 숫자, 특수문자를 사용하세요.")
  private String password;

  @NotBlank
  @Pattern(regexp = "^.{2,10}$", message = "이름은 2~10자이어야 합니다.")
  private String name;

  @NotBlank
  @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호는 010-1234-5678 형식으로 입력해주세요.")
  private String phone;

  @NotBlank(message = "주소는 필수 입력 값입니다.")
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
