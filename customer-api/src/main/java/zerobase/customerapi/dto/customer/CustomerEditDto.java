package zerobase.customerapi.dto.customer;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 고객 정보 수정을 위한 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEditDto {

  private String customerKey;
  private String name;
  private String phone;
  private String address;
  private LocalDate birth;
}
