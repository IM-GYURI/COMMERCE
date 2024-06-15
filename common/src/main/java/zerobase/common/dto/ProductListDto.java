package zerobase.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.type.Category;

/**
 * 상품 리스트 조회를 위한 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDto {

  private String name;
  private Category category;
  private Long price;
  private Long stock;
}
