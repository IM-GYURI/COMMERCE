package zerobase.sellerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 판매자 정보 수정을 위한 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerEditDto {

  private String sellerKey;
  private String name;
  private String phone;
  private String address;
}
