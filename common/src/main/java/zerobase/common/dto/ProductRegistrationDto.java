package zerobase.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.entity.ProductEntity;
import zerobase.common.type.Category;
import zerobase.common.util.ValidEnum;

/**
 * 상품 등록용 Dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegistrationDto {

  @NotBlank
  private String sellerKey;

  @NotBlank
  private String name;

  @ValidEnum(enumClass = Category.class)
  private Category category;

  @NotNull
  private Long price;

  @NotNull
  private Long stock;

  @NotBlank
  private String description;
  
  public ProductEntity toEntity(String productKey) {
    return ProductEntity.builder()
        .productKey(productKey)
        .name(name)
        .category(category)
        .price(price)
        .stock(stock)
        .description(description)
        .build();
  }

}
