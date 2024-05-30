package zerobase.customerapi.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import zerobase.customerapi.entity.CustomerEntity;

/**
 * 회원가입 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignUpDto {

  @NotBlank(message = "이메일은 필수 입력 값 입니다.")
  @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
  @Email(message = "이메일 형식에 맞지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
  private String password;

  @NotBlank
  private String name;

  @NotBlank
  private String phone;

  @NotBlank
  private String address;

  @NotNull(message = "생년월일은 필수 입력 값 입니다.")
  @Past(message = "생년월일을 확인해주세요.")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  /**
   * 고객 키와 비밀번호를 받아 CustomerEntity 빌드
   */
  public CustomerEntity toEntity(String customerKey, String password) {
    return CustomerEntity.builder()
        .customerKey(customerKey)
        .email(email)
        .password(password)
        .name(name)
        .phone(phone)
        .address(address)
        .point(0L)
        .birth(birth)
        .build();
  }
}
