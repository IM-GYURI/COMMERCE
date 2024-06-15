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
import zerobase.common.dto.ProductDto;
import zerobase.common.dto.ProductEditDto;
import zerobase.common.dto.ProductRegistrationDto;
import zerobase.sellerapi.service.SellerProductService;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class SellerProductController {

  private final SellerProductService sellerProductService;

  /**
   * 상품 등록 : SELLER만 가능
   *
   * @param productRegistrationDto
   * @return
   */
  @PreAuthorize("hasRole('SELLER')")
  @PostMapping("/registration")
  public ResponseEntity<?> registration(
      @RequestBody @Valid ProductRegistrationDto productRegistrationDto) {
    ProductDto productDto = sellerProductService.registration(productRegistrationDto);

    return ResponseEntity.ok(productDto);
  }

  /**
   * 상품 정보 수정 : SELLER 본인만 가능
   *
   * @param productEditDto
   * @return
   */
  @PreAuthorize("hasRole('SELLER')")
  @PatchMapping("/{productKey}")
  public ResponseEntity<?> editProductInformation(@PathVariable String productKey,
      @RequestHeader("Authorization") String token,
      @RequestBody @Valid ProductEditDto productEditDto) {
    ProductDto productDto = sellerProductService.validateAuthorizationAndGetSeller(productKey,
        token);

    return ResponseEntity.ok(sellerProductService.edit(productEditDto));
  }

  /**
   * 상품 삭제 : SELLER 본인만 가능
   *
   * @param productKey
   * @param token
   * @return
   */
  @PreAuthorize("hasRole('SELLER')")
  @DeleteMapping("/{productKey}")
  public ResponseEntity<?> deleteProduct(@PathVariable String productKey,
      @RequestHeader("Authorization") String token) {
    ProductDto productDto = sellerProductService.validateAuthorizationAndGetSeller(productKey,
        token);

    productKey = sellerProductService.delete(productKey, token);

    return ResponseEntity.ok("delete " + productKey);
  }
}
