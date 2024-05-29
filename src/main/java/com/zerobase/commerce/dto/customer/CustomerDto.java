package com.zerobase.commerce.dto.customer;

import com.zerobase.commerce.entity.CustomerEntity;
import com.zerobase.commerce.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

  private String customerKey;
  private String name;
  private String email;
  private Role role;

  /**
   * CustomerEntity -> CustomerDto
   */
  public static CustomerDto fromEntity(CustomerEntity customer) {
    return CustomerDto.builder()
        .customerKey(customer.getCustomerKey())
        .name(customer.getName())
        .email(customer.getEmail())
        .role(customer.getRole())
        .build();
  }
}
