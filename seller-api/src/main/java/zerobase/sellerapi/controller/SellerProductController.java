package zerobase.sellerapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.sellerapi.dto.product.EditDto;
import zerobase.sellerapi.dto.product.ProductDto;
import zerobase.sellerapi.dto.product.RegistrationDto;
import zerobase.sellerapi.exception.SellerCustomException;
import zerobase.sellerapi.service.SellerProductService;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class SellerProductController {

  private final SellerProductService sellerProductService;

  /**
   * 상품 등록 : SELLER만 가능
   *
   * @param registrationDto
   * @return
   */

  @PreAuthorize("hasRole('SELLER')")
  @PostMapping("/registration")
  public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDto registrationDto) {
    ProductDto productDto = sellerProductService.registration(registrationDto);

    return ResponseEntity.ok(productDto);
  }

  /**
   * 상품 정보 수정 : SELLER 본인만 가능
   *
   * @param editDto
   * @return
   */
  @PreAuthorize("hasRole('SELLER')")
  @PatchMapping("/{productKey}")
  public ResponseEntity<?> editProductInformation(@PathVariable String productKey,
      @RequestHeader("Authorization") String token, @RequestBody @Valid EditDto editDto) {
    try {
      ProductDto productDto = sellerProductService.validateAuthorizationAndGetSeller(productKey,
          token);
    } catch (SellerCustomException e) {
      return ResponseEntity.status(403).body("IS NOT SAME SELLER");
    }

    return ResponseEntity.ok(sellerProductService.edit(editDto));
  }

  @PreAuthorize("hasRole('SELLER')")
  @DeleteMapping("/{productKey}")
  public ResponseEntity<?> deleteProduct(@PathVariable String productKey,
      @RequestHeader("Authorization") String token) {
    try {
      ProductDto productDto = sellerProductService.validateAuthorizationAndGetSeller(productKey,
          token);
    } catch (SellerCustomException e) {
      return ResponseEntity.status(403).body("IS NOT SAME SELLER");
    }

    productKey = sellerProductService.delete(productKey, token);

    return ResponseEntity.ok("delete " + productKey);
  }
}
