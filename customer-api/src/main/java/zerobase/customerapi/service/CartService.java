package zerobase.customerapi.service;

import static zerobase.customerapi.exception.CustomerErrorCode.CART_ALREADY_EXISTS;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.exception.CommonCustomException;
import zerobase.customerapi.entity.CartEntity;
import zerobase.customerapi.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;

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
}
