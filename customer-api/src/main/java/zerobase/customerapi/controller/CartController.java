package zerobase.customerapi.controller;

import static zerobase.customerapi.exception.CustomerErrorCode.CART_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.common.exception.CommonCustomException;
import zerobase.customerapi.dto.cart.CartDto;
import zerobase.customerapi.dto.customer.CustomerDto;
import zerobase.customerapi.entity.CartEntity;
import zerobase.customerapi.repository.CartRepository;
import zerobase.customerapi.security.CustomerTokenProvider;
import zerobase.customerapi.service.CartService;
import zerobase.customerapi.service.CustomerService;

/**
 * CUSTOMER만 사용 가능
 */
@RequestMapping("/cart")
@RequiredArgsConstructor
@RestController
public class CartController {

  private final CustomerService customerService;
  private final CartService cartService;
  private final CartRepository cartRepository;
  private final CustomerTokenProvider customerTokenProvider;

  @PreAuthorize("hasRole('CUSTOMER')")
  @GetMapping("/{customerKey}")
  public ResponseEntity<?> cartInformation(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);

    CartEntity cartEntity = cartRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CART_NOT_FOUND));

    return ResponseEntity.ok(CartDto.fromEntity(cartEntity));
  }
}
