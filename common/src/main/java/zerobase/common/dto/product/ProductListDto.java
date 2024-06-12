package zerobase.common.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.type.Category;

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
