package zerobase.sellerapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.sellerapi.dto.product.ProductDto;
import zerobase.sellerapi.dto.product.RegistrationDto;
import zerobase.sellerapi.service.ProductService;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class ProductController {

  private final ProductService productService;

  /**
   * 상품 등록 SELLER 권한을 가지고 있을 경우만 가능
   */

  @PreAuthorize("hasRole('SELLER')")
  @PostMapping("/registration")
  public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDto registrationDto) {
    ProductDto productDto = productService.registration(registrationDto);

    return ResponseEntity.ok(productDto);
  }
}
