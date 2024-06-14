package zerobase.customerapi.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.common.dto.ProductListDto;
import zerobase.common.type.Category;
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
  @GetMapping("/name")
  public ResponseEntity<?> sortedByName() {
    List<ProductListDto> productList = customerProductService.getAllSortedProductListByName();

    return ResponseEntity.ok(productList);
  }

  /**
   * 상품 전체 조회 : 낮은 가격순
   */
  @GetMapping("/price-asc")
  public ResponseEntity<?> sortedByPriceAsc() {
    List<ProductListDto> productList = customerProductService.getAllSortedProductListByPriceAsc();

    return ResponseEntity.ok(productList);
  }

  /**
   * 상품 전체 조회 : 높은 가격순
   */
  @GetMapping("/price-desc")
  public ResponseEntity<?> sortedByPriceDesc() {
    List<ProductListDto> productList = customerProductService.getAllSortedProductListByPriceDesc();

    return ResponseEntity.ok(productList);
  }

  /**
   * 상품 검색 : 가나다순
   *
   * @param keyword
   * @return
   */
  @GetMapping("/search/name")
  public ResponseEntity<?> searchByKeyword(@RequestParam String keyword) {
    return ResponseEntity.ok(customerProductService.searchByKeyword(keyword));
  }

  /**
   * 상품 검색 : 낮은 가격순
   *
   * @param keyword
   * @return
   */
  @GetMapping("/search/price-asc")
  public ResponseEntity<?> searchByKeywordSortedByPriceAsc(@RequestParam String keyword) {
    return ResponseEntity.ok(customerProductService.searchByKeywordSortedByPriceAsc(keyword));
  }

  /**
   * 상품 검색 : 높은 가격순
   *
   * @param keyword
   * @return
   */
  @GetMapping("/search/price-desc")
  public ResponseEntity<?> searchByKeywordSortedByPriceDesc(@RequestParam String keyword) {
    return ResponseEntity.ok(customerProductService.searchByKeywordSortedByPriceDesc(keyword));
  }

  /**
   * 카테고리 검색 : 가나다순
   *
   * @param category
   * @return
   */
  @GetMapping("/search/category/name")
  public ResponseEntity<?> searchByCategorySortedByName(@RequestParam Category category) {
    return ResponseEntity.ok(customerProductService.searchByCategorySortedByName(category));
  }

  /**
   * 카테고리 검색 : 낮은 가격순
   *
   * @param category
   * @return
   */
  @GetMapping("/search/category/price-asc")
  public ResponseEntity<?> searchByCategorySortedByPriceAsc(@RequestParam Category category) {
    return ResponseEntity.ok(customerProductService.searchByCategorySortedByPriceAsc(category));
  }

  /**
   * 카테고리 검색 : 높은 가격순
   *
   * @param category
   * @return
   */
  @GetMapping("/search/category/price-desc")
  public ResponseEntity<?> searchByCategorySortedByPriceDesc(@RequestParam Category category) {
    return ResponseEntity.ok(customerProductService.searchByCategorySortedByPriceDesc(category));
  }
}
