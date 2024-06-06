package zerobase.sellerapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.sellerapi.type.Category;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditDto {

  private String productKey;
  private String sellerKey;
  private String name;
  private Category category;
  private Long price;
  private Long stock;
  private String description;
}