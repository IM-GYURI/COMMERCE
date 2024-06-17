package zerobase.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.type.Category;

/**
 * 상품 정보 수정을 위한 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEditDto {

  private String productKey;
  private String sellerKey;
  private String name;
  private Category category;
  private Long price;
  private Long stock;
  private String description;
}
