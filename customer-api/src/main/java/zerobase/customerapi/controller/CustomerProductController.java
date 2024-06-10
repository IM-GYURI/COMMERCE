package zerobase.customerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.customerapi.service.CustomerProductService;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class CustomerProductController {

  private final CustomerProductService customerProductService;

  @GetMapping("/{productKey}")
  public ResponseEntity<?> productDetail(@PathVariable String productKey) {
    return ResponseEntity.ok(customerProductService.information(productKey));
  }
}
