package zerobase.customerapi.service;

import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.dto.product.ProductDto;
import zerobase.common.dto.product.ProductListDto;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerProductService {

  private final ProductRepository productRepository;

  /**
   * 상품 상세 정보 조회
   *
   * @param productKey
   * @return
   */
  public ProductDto information(String productKey) {
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
    List<ProductEntity> productList = productRepository.findAll();

    return productList.stream()
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
    List<ProductEntity> productList = productRepository.findAll();

    return productList.stream()
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
    List<ProductEntity> productList = productRepository.findAll();

    return productList.stream()
        .sorted(Comparator.comparing(ProductEntity::getPrice).reversed())
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }

  /**
   * 상품 검색 : 키워드를 통한 상품명 검색
   *
   * @param keyword
   * @return
   */
  public List<ProductListDto> searchByKeyword(String keyword) {
    Pageable limit = PageRequest.of(0, 20);
    return productRepository.findAllByNameContains(keyword, limit)
        .stream()
        .map(productEntity -> new ProductListDto(productEntity.getName(),
            productEntity.getCategory(), productEntity.getPrice(), productEntity.getStock()))
        .collect(Collectors.toList());
  }
}
