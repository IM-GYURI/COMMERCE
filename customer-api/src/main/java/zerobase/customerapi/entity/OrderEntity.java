package zerobase.customerapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.common.entity.BaseEntity;

/**
 * 주문 엔티티 : 아이디, 고객 키, OrderItemEntity 리스트, 총 가격
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Orders")
public class OrderEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String customerKey;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<OrderItemEntity> items;

  @Column(nullable = false)
  private Long totalPrice;

  @Builder
  public OrderEntity(String customerKey, Long totalPrice, List<OrderItemEntity> items) {
    this.customerKey = customerKey;
    this.totalPrice = totalPrice;
    this.items = items != null ? items : new ArrayList<>();
  }

  public void addItems(OrderItemEntity orderItemEntity) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(orderItemEntity);
  }
}
