package zerobase.sellerapi.dto.product;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.sellerapi.entity.ProductEntity;
import zerobase.sellerapi.type.Category;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  private String productKey;
  private String sellerKey;
  private String name;
  private Category category;
  private Long price;
  private Long stock;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  /**
   * ProductEntity -> ProductDto 변환
   */
  public static ProductDto fromEntity(ProductEntity product) {
    return ProductDto.builder()
        .productKey(product.getProductKey())
        .sellerKey(product.getSellerEntity().getSellerKey())
        .name(product.getName())
        .category(product.getCategory())
        .price(product.getPrice())
        .stock(product.getStock())
        .description(product.getDescription())
        .createdAt(product.getCreatedAt())
        .modifiedAt(product.getModifiedAt())
        .build();
  }
}
