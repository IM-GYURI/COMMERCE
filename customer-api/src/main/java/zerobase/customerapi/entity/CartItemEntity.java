package zerobase.customerapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.entity.BaseEntity;

/**
 * 장바구니 아이템 엔티티 : 아이디, 장바구니 아이디(FK), 상품 키, 가격, 수량
 */
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "cart_id")})
@Entity(name = "CartItem")
public class CartItemEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cart_id")
  private CartEntity cart;

  private String productKey;

  private Long price;

  private Long count;

  @Builder
  public CartItemEntity(CartEntity cartEntity, String productKey, Long price, Long count) {
    this.cart = cartEntity;
    this.productKey = productKey;
    this.price = price;
    this.count = count;
  }

  public void updateCount(Long count) {
    this.count = count;
  }
}
