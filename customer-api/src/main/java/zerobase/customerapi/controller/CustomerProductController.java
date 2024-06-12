package zerobase.customerapi.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.common.dto.product.ProductListDto;
import zerobase.customerapi.service.CustomerProductService;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class CustomerProductController {

  private final CustomerProductService customerProductService;

  /**
   * 상품 상세 조회
   *
   * @param productKey
   * @return
   */
  @GetMapping("/{productKey}")
  public ResponseEntity<?> productDetail(@PathVariable String productKey) {
    return ResponseEntity.ok(customerProductService.information(productKey));
  }

  /**
   * 상품 전체 조회 : 가나다순
   *
   * @return
   */
  @GetMapping("/sorted-by-name")
  public ResponseEntity<?> sortedByName() {
    List<ProductListDto> productList = customerProductService.getAllSortedProductListByName();

    return ResponseEntity.ok(productList);
  }
}
