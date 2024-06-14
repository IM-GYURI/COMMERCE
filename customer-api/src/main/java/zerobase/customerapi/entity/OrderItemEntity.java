package zerobase.customerapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 아이템 엔티티 : 아이디, 주문 아이디(FK), 상품 키, 가격, 수량
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "OrderItem")
public class OrderItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @Column(nullable = false)
  private String productKey;

  private Long price;

  @Column(nullable = false)
  private Long count;

}
