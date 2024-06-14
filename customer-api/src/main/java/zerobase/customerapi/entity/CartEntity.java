package zerobase.customerapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 엔티티 : 아이디, 고객 키, CartItemEntity 리스트
 */
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "customer_key")})
@Entity(name = "Cart")
public class CartEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, name = "customer_key")
  private String customerKey;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<CartItemEntity> items = new ArrayList<>();

  @Builder
  public CartEntity(String customerKey) {
    this.customerKey = customerKey;
  }
}
