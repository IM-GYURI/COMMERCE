package zerobase.customerapi.dto.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.type.Role;
import zerobase.customerapi.entity.CustomerEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

  private String customerKey;
  private String email;
  private String name;
  private String phone;
  private String address;
  private Long point;
  private LocalDate birth;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Role role;

  /**
   * CustomerEntity -> CustomerDto
   */
  public static CustomerDto fromEntity(CustomerEntity customer) {
    return CustomerDto.builder()
        .customerKey(customer.getCustomerKey())
        .email(customer.getEmail())
        .name(customer.getName())
        .phone(customer.getPhone())
        .address(customer.getAddress())
        .point(customer.getPoint())
        .birth(customer.getBirth())
        .createdAt(customer.getCreatedAt())
        .modifiedAt(customer.getModifiedAt())
        .role(customer.getRole())
        .build();
  }
}
