package zerobase.customerapi.service;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.dto.ProductDto;
import zerobase.common.dto.ProductListDto;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;
import zerobase.common.type.Category;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomerProductService {

  private final ProductRepository productRepository;

  /**
   * 상품 상세 정보 조회
   *
   * @param productKey
   * @return
   */
  public ProductDto information(String productKey) {
    log.info("Product detail information : " + productKey);
    return ProductDto.fromEntity(findByProductKeyOrThrow(productKey));
  }

  /**
   * 상품 키를 통해 상품이 존재하는지 확인
   *
   * @param productKey
   * @return
   */
  public ProductEntity findByProductKeyOrThrow(String productKey) {
    return productRepository.findByProductKey(productKey)
        .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND));
  }

  /**
   * 상품 전체 조회 : 가나다순
   *
   * @return
   */
  public List<ProductListDto> getAllSortedProductListByName() {
    Pageable limit = PageRequest.of(0, 20, Sort.by(ASC, "name"));

    log.info("all product list sorted by name asc");

    return productRepository.findAll(limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getName))
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 상품 전체 조회 : 낮은 가격순
   *
   * @return
   */
  public List<ProductListDto> getAllSortedProductListByPriceAsc() {
    Pageable limit = PageRequest.of(0, 20, Sort.by(ASC, "price"));

    log.info("all product list sorted by price asc");

    return productRepository.findAll(limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getPrice))
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 상품 전체 조회 : 높은 가격순
   *
   * @return
   */
  public List<ProductListDto> getAllSortedProductListByPriceDesc() {
    Pageable limit = PageRequest.of(0, 20, Sort.by(DESC, "price"));

    log.info("all product list sorted by price desc");

    return productRepository.findAll(limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getPrice).reversed())
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 상품 검색 : 가나다순
   *
   * @param keyword
   * @return
   */
  public List<ProductListDto> searchByKeyword(String keyword) {
    Pageable limit = PageRequest.of(0, 20, Sort.by(ASC, "name"));

    log.info("product keyword search sorted by name asc : " + keyword);

    return productRepository.findAllByNameContains(keyword, limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getName))
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 상품 검색 : 낮은 가격순
   *
   * @param keyword
   * @return
   */
  public List<ProductListDto> searchByKeywordSortedByPriceAsc(String keyword) {
    Pageable limit = PageRequest.of(0, 20, Sort.by(ASC, "price"));

    log.info("product keyword search sorted by price asc : " + keyword);

    return productRepository.findAllByNameContains(keyword, limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getPrice))
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 상품 검색 : 높은 가격순
   *
   * @param keyword
   * @return
   */
  public List<ProductListDto> searchByKeywordSortedByPriceDesc(String keyword) {
    Pageable limit = PageRequest.of(0, 20, Sort.by(DESC, "price"));

    log.info("product keyword search sorted by price desc : " + keyword);

    return productRepository.findAllByNameContains(keyword, limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getPrice).reversed())
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 카테고리 검색 : 가나다순
   *
   * @param category
   * @return
   */
  public List<ProductListDto> searchByCategorySortedByName(Category category) {
    Pageable limit = PageRequest.of(0, 20, Sort.by(ASC, "name"));

    log.info("product category search sorted by name asc : " + category);

    return productRepository.findAllByCategory(category, limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getName))
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 카테고리 검색 : 낮은 가격순
   *
   * @param category
   * @return
   */
  public List<ProductListDto> searchByCategorySortedByPriceAsc(Category category) {
    Pageable limit = PageRequest.of(0, 20, Sort.by(ASC, "price"));

    log.info("product category search sorted by price asc : " + category);

    return productRepository.findAllByCategory(category, limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getPrice))
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 카테고리 검색 : 높은 가격순
   *
   * @param category
   * @return
   */
  public List<ProductListDto> searchByCategorySortedByPriceDesc(Category category) {
    Pageable limit = PageRequest.of(0, 20, Sort.by(DESC, "price"));

    log.info("product category search sorted by price desc : " + category);

    return productRepository.findAllByCategory(category, limit).stream()
        .sorted(Comparator.comparing(ProductEntity::getPrice).reversed())
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }
}
