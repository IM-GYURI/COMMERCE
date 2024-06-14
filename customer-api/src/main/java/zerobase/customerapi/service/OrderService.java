package zerobase.customerapi.service;

import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;
import static zerobase.customerapi.exception.CustomerErrorCode.CART_NOT_FOUND;
import static zerobase.customerapi.exception.CustomerErrorCode.CUSTOMER_NOT_FOUND;
import static zerobase.customerapi.exception.CustomerErrorCode.ORDER_NOT_EXISTS;
import static zerobase.customerapi.exception.CustomerErrorCode.POINT_NOT_ENOUGH;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;
import zerobase.customerapi.dto.order.OrderDto;
import zerobase.customerapi.entity.CartEntity;
import zerobase.customerapi.entity.CartItemEntity;
import zerobase.customerapi.entity.CustomerEntity;
import zerobase.customerapi.entity.OrderEntity;
import zerobase.customerapi.entity.OrderItemEntity;
import zerobase.customerapi.repository.CartRepository;
import zerobase.customerapi.repository.CustomerRepository;
import zerobase.customerapi.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final CartService cartService;
  private final CustomerRepository customerRepository;

  /**
   * 장바구니 전체를 주문 목록으로 보고 결제하는 기능
   * <p>
   * 결제해야하는 가격이 고객의 현재 포인트보다 크면 결제 불가
   * <p>
   * 상품을 주문한 수량만큼 재고를 감소시킴
   * <p>
   * 결제 후 장바구니 전체 비우기
   *
   * @param customerKey
   * @return
   */
  @Transactional
  public OrderDto makeOrder(String customerKey) {
    CartEntity cartEntity = cartRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CART_NOT_FOUND));

    CustomerEntity customerEntity = customerRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CUSTOMER_NOT_FOUND));

    Long totalPrice = cartEntity.getItems().stream()
        .mapToLong(item -> item.getPrice() * item.getCount())
        .sum();

    if (customerEntity.getPoint() < totalPrice) {
      throw new CommonCustomException(POINT_NOT_ENOUGH);
    }

    customerEntity.minusPoint(totalPrice);
    customerRepository.save(customerEntity);

    log.info("Customer point decreased about " + totalPrice + " : " + customerKey);

    OrderEntity orderEntity = OrderEntity.builder()
        .customerKey(customerKey)
        .items(new ArrayList<>())
        .totalPrice(totalPrice)
        .build();

    for (CartItemEntity cartItemEntity : cartEntity.getItems()) {
      ProductEntity productEntity = productRepository.findByProductKey(
              cartItemEntity.getProductKey())
          .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND));

      productEntity.decreaseStock(cartItemEntity.getCount());
      productRepository.save(productEntity);

      log.info("Product stock decreased about " + cartItemEntity.getCount() + " : "
          + productEntity.getProductKey());

      OrderItemEntity orderItemEntity = OrderItemEntity.builder()
          .order(orderEntity)
          .productKey(cartItemEntity.getProductKey())
          .price(cartItemEntity.getPrice())
          .count(cartItemEntity.getCount())
          .build();

      orderEntity.addItems(orderItemEntity);
    }

    orderRepository.save(orderEntity);

    log.info("Customer made order : " + customerKey);

    cartService.clearCart(customerKey);

    log.info("Cart is cleared : " + customerKey);

    return OrderDto.fromEntity(orderEntity);
  }

  @Transactional(readOnly = true)
  public List<OrderDto> getOrders(String customerKey) {
    List<OrderEntity> orders = orderRepository.findAllByCustomerKey(customerKey);

    if (orders.isEmpty()) {
      throw new CommonCustomException(ORDER_NOT_EXISTS);
    }

    log.info("Customer get own list of orders : " + customerKey);

    return orders.stream()
        .map(OrderDto::fromEntity)
        .collect(Collectors.toList());
  }
}
