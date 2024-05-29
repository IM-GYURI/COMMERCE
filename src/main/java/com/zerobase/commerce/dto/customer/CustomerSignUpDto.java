package com.zerobase.commerce.dto.customer;

import com.zerobase.commerce.entity.CustomerEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignUpDto {

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

  @NotBlank
  private String birth;

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
        .birth(birth)
        .build();
  }
}
