package zerobase.customerapi.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 관련 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPointDto {

  private String customerKey;
  private Long point;
}
