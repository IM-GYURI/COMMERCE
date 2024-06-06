package zerobase.sellerapi.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditDto {

  private String sellerKey;
  private String name;
  private String phone;
  private String address;
}
