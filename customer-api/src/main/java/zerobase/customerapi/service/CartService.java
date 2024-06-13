package zerobase.customerapi.service;

import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;
import static zerobase.common.exception.CommonErrorCode.PRODUCT_STOCK_NOT_ENOUGH;
import static zerobase.customerapi.exception.CustomerErrorCode.CART_ALREADY_EXISTS;
import static zerobase.customerapi.exception.CustomerErrorCode.CART_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;
import zerobase.customerapi.dto.cart.CartDto;
import zerobase.customerapi.dto.cart.CartItemRegisterDto;
import zerobase.customerapi.entity.CartEntity;
import zerobase.customerapi.entity.CartItemEntity;
import zerobase.customerapi.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  @Transactional
  public void cartRegister(String customerKey) {
    validateCartExistsByCustomerKey(customerKey);

    CartEntity cartEntity = CartEntity.builder()
        .customerKey(customerKey)
        .build();

    cartRepository.save(cartEntity);
  }

  private void validateCartExistsByCustomerKey(String customerKey) {
    if (cartRepository.existsByCustomerKey(customerKey)) {
      throw new CommonCustomException(CART_ALREADY_EXISTS);
    }
  }

  @Transactional
  public void addProductToCart(String customerKey, CartItemRegisterDto cartItemRegisterDto) {
    String productKey = cartItemRegisterDto.getProductKey();
    ProductEntity productEntity = productRepository.findByProductKey(productKey)
        .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND));

    Long price = productEntity.getPrice();
    Long count = cartItemRegisterDto.getCount();

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

      existingItem.updateCount(count);
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

  @Transactional
  public CartDto getCartByCustomerKey(String customerKey) {
    return CartDto.fromEntity(cartRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CART_NOT_FOUND)));
  }
}