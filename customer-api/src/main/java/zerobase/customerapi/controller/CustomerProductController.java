package zerobase.customerapi.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  /**
   * 상품 전체 조회 : 낮은 가격순
   */
  @GetMapping("/sorted-by-price-asc")
  public ResponseEntity<?> sortedByPriceAsc() {
    List<ProductListDto> productList = customerProductService.getAllSortedProductListByPriceAsc();

    return ResponseEntity.ok(productList);
  }

  /**
   * 상품 전체 조회 : 낮은 가격순
   */
  @GetMapping("/sorted-by-price-desc")
  public ResponseEntity<?> sortedByPriceDesc() {
    List<ProductListDto> productList = customerProductService.getAllSortedProductListByPriceDesc();

    return ResponseEntity.ok(productList);
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchByKeyword(@RequestParam String keyword) {
    return ResponseEntity.ok(customerProductService.searchByKeyword(keyword));
  }
}
