package zerobase.customerapi.dto.customer;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditDto {

  private String customerKey;
  private String name;
  private String phone;
  private String address;
  private LocalDate birth;
}
