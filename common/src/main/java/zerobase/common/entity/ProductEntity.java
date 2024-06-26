package zerobase.common.entity;

import static zerobase.common.exception.CommonErrorCode.PRODUCT_STOCK_NOT_ENOUGH;
import static zerobase.common.exception.CommonErrorCode.STOCK_NOT_MINUS;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zerobase.common.dto.ProductEditDto;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.type.Category;

/**
 * 상품 엔티티 : 아이디, 상품 키, 판매자 키,이름, 카테고리, 가격, 재고, 설명
 */
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "product_key")})
@Entity(name = "Product")
public class ProductEntity extends BaseEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, name = "product_key")
  private String productKey;

  @Column(nullable = false)
  private String sellerKey;

  @Column(nullable = false, unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private Long stock;

  @Column
  private String description;

  @Builder
  public ProductEntity(String productKey, String name, Category category, Long price, Long stock,
      String description) {
    this.productKey = productKey;
    this.name = name;
    this.category = category;
    this.price = price;
    this.stock = stock;
    this.description = description;
  }

  /**
   * Seller 설정
   *
   * @return
   */
  public void setSeller(String sellerKey) {
    this.sellerKey = sellerKey;
  }

  public void updateProduct(ProductEditDto productEditDto) {
    this.name = productEditDto.getName();
    this.category = productEditDto.getCategory();
    this.price = productEditDto.getPrice();
    this.stock = productEditDto.getStock();
    this.description = productEditDto.getDescription();
  }

  /**
   * 상품 재고 감소
   *
   * @param count
   */
  public void decreaseStock(Long count) {
    if (count < 0) {
      throw new CommonCustomException(STOCK_NOT_MINUS);
    }

    if (this.stock < count) {
      throw new CommonCustomException(PRODUCT_STOCK_NOT_ENOUGH);
    }

    this.stock -= count;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
