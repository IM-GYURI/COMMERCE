package zerobase.customerapi.controller;

import static zerobase.customerapi.exception.CustomerErrorCode.CART_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.common.exception.CommonCustomException;
import zerobase.customerapi.dto.cart.CartItemEditDto;
import zerobase.customerapi.dto.cart.CartItemRegisterDto;
import zerobase.customerapi.dto.cart.CartWithTotalDto;
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

  /**
   * 장바구니 조회
   *
   * @param customerKey
   * @param token
   * @return
   */
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

    return ResponseEntity.ok(CartWithTotalDto.fromEntity(cartEntity));
  }

  /**
   * 장바구니에 상품 추가하기
   *
   * @param customerKey
   * @param token
   * @param cartItemRegisterDto
   * @return
   */
  @PreAuthorize("hasRole('CUSTOMER')")
  @PostMapping("/add-product/{customerKey}")
  public ResponseEntity<?> addProductToCart(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token,
      @RequestBody CartItemRegisterDto cartItemRegisterDto) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);

    cartService.addProductToCart(customerKey, cartItemRegisterDto);

    return ResponseEntity.ok(cartService.getCartByCustomerKey(customerKey));
  }

  /**
   * 장바구니의 상품 수량 수정하기
   *
   * @param customerKey
   * @param token
   * @param cartItemEditDto
   * @return
   */
  @PreAuthorize("hasRole('CUSTOMER')")
  @PatchMapping("/edit-product-quantity/{customerKey}")
  public ResponseEntity<?> editCartProductQuantity(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token, @RequestBody CartItemEditDto cartItemEditDto) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);

    cartService.editCartProductQuantity(customerKey, cartItemEditDto);

    return ResponseEntity.ok(cartService.getCartByCustomerKey(customerKey));
  }

  /**
   * 장바구니 전체 비우기
   *
   * @param customerKey
   * @param token
   * @return
   */
  @PreAuthorize("hasRole('CUSTOMER')")
  @DeleteMapping("/clear/{customerKey}")
  public ResponseEntity<?> clearCart(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);

    cartService.clearCart(customerKey);

    return ResponseEntity.ok(cartService.getCartByCustomerKey(customerKey));
  }
}
