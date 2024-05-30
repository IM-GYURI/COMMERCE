package zerobase.customerapi.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.customerapi.entity.CustomerEntity;
import zerobase.customerapi.type.Role;

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
