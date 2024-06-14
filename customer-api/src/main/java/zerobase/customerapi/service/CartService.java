package zerobase.customerapi.service;

import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;
import static zerobase.common.exception.CommonErrorCode.PRODUCT_STOCK_NOT_ENOUGH;
import static zerobase.customerapi.exception.CustomerErrorCode.CART_ALREADY_EXISTS;
import static zerobase.customerapi.exception.CustomerErrorCode.CART_INVALID_PRODUCT_COUNT;
import static zerobase.customerapi.exception.CustomerErrorCode.CART_ITEM_NOT_FOUND;
import static zerobase.customerapi.exception.CustomerErrorCode.CART_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;
import zerobase.customerapi.dto.cart.CartItemEditDto;
import zerobase.customerapi.dto.cart.CartItemRegisterDto;
import zerobase.customerapi.entity.CartEntity;
import zerobase.customerapi.entity.CartItemEntity;
import zerobase.customerapi.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  /**
   * 장바구니 등록
   *
   * @param customerKey
   */
  @Transactional
  public void cartRegister(String customerKey) {
    validateCartExistsByCustomerKey(customerKey);

    CartEntity cartEntity = CartEntity.builder()
        .customerKey(customerKey)
        .build();

    cartRepository.save(cartEntity);
  }

  /**
   * 해당 고객의 장바구니가 이미 존재하는지 여부 확인
   *
   * @param customerKey
   */
  private void validateCartExistsByCustomerKey(String customerKey) {
    if (cartRepository.existsByCustomerKey(customerKey)) {
      throw new CommonCustomException(CART_ALREADY_EXISTS);
    }
  }

  /**
   * 장바구니에 상품 담기
   * <p>
   * 만약 담으려는 상품의 수량이 음수라면 CART_INVALID_PRODUCT_COUNT 에러 던지기
   * <p>
   * 만약 담으려는 상품이 이미 장바구니에 존재한다면 상품의 재고가 넘지 않는 한에서 수량을 더하도록 함
   * <p>
   * 만약 담으려는 상품이 장바구니에 존재하지 않는다면 상품의 재고가 넘지 않는 한에서 추가하도록 함
   *
   * @param customerKey
   * @param cartItemRegisterDto
   */
  @Transactional
  public void addProductToCart(String customerKey, CartItemRegisterDto cartItemRegisterDto) {
    String productKey = cartItemRegisterDto.getProductKey();
    ProductEntity productEntity = productRepository.findByProductKey(productKey)
        .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND));

    Long price = productEntity.getPrice();
    Long count = cartItemRegisterDto.getCount();

    if (count < 0) {
      throw new CommonCustomException(CART_INVALID_PRODUCT_COUNT);
    }

    CartEntity cartEntity = cartRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CART_NOT_FOUND));

    CartItemEntity existingItem = cartEntity.getItems().stream()
        .filter(item -> item.getProductKey().equals(productKey))
        .findFirst().orElse(null);

    if (existingItem != null) {
      long updatedCount = existingItem.getCount() + count;

      if (updatedCount > productEntity.getStock()) {
        throw new CommonCustomException(PRODUCT_STOCK_NOT_ENOUGH);
      }

      existingItem.updateCount(existingItem.getCount() + count);
    } else {
      if (count > productEntity.getStock()) {
        throw new CommonCustomException(PRODUCT_STOCK_NOT_ENOUGH);
      }

      CartItemEntity newItem = CartItemEntity.builder()
          .cartEntity(cartEntity)
          .productKey(productKey)
          .price(price)
          .count(count)
          .build();

      cartEntity.getItems().add(newItem);
    }

    cartRepository.save(cartEntity);
  }

  /**
   * 이미 장바구니에 담겨있는 상품의 수량을 수정하는 기능
   * <p>
   * 만약 해당 상품이 장바구니에 존재하지 않는다면 CART_ITEM_NOT_FOUND 에러 던지기
   * <p>
   * 만약 해당 상품의 수량을 음수로 설정한다면 CART_INVALID_PRODUCT_COUNT 에러 던지기
   * <p>
   * 먄약해당 상품의 수량을 0으로 설정한다면 해당 상품을 장바구니에서 제거하도록 함
   * <p>
   * 해당 상품의 수량이 상품의 재고를 넘지 않는 한에서 수량을 수정할 수 있도록 함
   *
   * @param customerKey
   * @param cartItemEditDto
   */
  @Transactional
  public void editCartProductQuantity(String customerKey, CartItemEditDto cartItemEditDto) {
    CartEntity cartEntity = cartRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CART_NOT_FOUND));

    CartItemEntity cartItemEntity = cartEntity.getItems().stream()
        .filter(item -> item.getProductKey().equals(cartItemEditDto.getProductKey()))
        .findFirst()
        .orElseThrow(() -> new CommonCustomException(CART_ITEM_NOT_FOUND));

    Long newCount = cartItemEditDto.getNewCount();

    if (newCount < 0) {
      throw new CommonCustomException(CART_INVALID_PRODUCT_COUNT);
    }

    if (newCount == 0) {
      cartEntity.getItems().remove(cartItemEntity);
      cartRepository.save(cartEntity);
      return;
    }

    Long productStock = productRepository.findByProductKey(cartItemEntity.getProductKey())
        .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND)).getStock();

    if (newCount > productStock) {
      throw new CommonCustomException(PRODUCT_STOCK_NOT_ENOUGH);
    }

    cartItemEntity.updateCount(newCount);
    cartRepository.save(cartEntity);
  }

  /**
   * 장바구니 전체 비우기
   *
   * @param customerKey
   */
  @Transactional
  public void clearCart(String customerKey) {
    CartEntity cartEntity = cartRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CART_NOT_FOUND));

    cartEntity.getItems().clear();
    cartRepository.save(cartEntity);
  }

  /**
   * 고객 키를 활용해서 장바구니를 찾도록 함
   *
   * @param customerKey
   * @return
   */
  public CartEntity getCartByCustomerKey(String customerKey) {
    return cartRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CART_NOT_FOUND));
  }
}