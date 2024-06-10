package zerobase.customerapi.service;

import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.dto.product.ProductDto;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerProductService {

  private final ProductRepository productRepository;

  public ProductDto information(String productKey) {
    return ProductDto.fromEntity(findByProductKeyOrThrow(productKey));
  }

  public ProductEntity findByProductKeyOrThrow(String productKey) {
    return productRepository.findByProductKey(productKey)
        .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND));
  }
}
