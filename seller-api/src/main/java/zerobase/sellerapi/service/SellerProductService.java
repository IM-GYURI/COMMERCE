package zerobase.sellerapi.service;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;
import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.dto.ProductDto;
import zerobase.common.dto.ProductEditDto;
import zerobase.common.dto.RegistrationDto;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;
import zerobase.common.util.KeyGenerator;
import zerobase.sellerapi.entity.SellerEntity;
import zerobase.sellerapi.security.SellerTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SellerProductService {

  private final ProductRepository productRepository;
  private final SellerService sellerService;
  private final SellerTokenProvider sellerTokenProvider;

  /**
   * 상품 등록 : 판매자 키를 통해 판매자 정보를 찾은 후 상품 등록
   *
   * @param registrationDto
   * @return
   */
  @Transactional
  public ProductDto registration(RegistrationDto registrationDto) {
    SellerEntity seller = sellerService.findBySellerKeyOrThrow(
        registrationDto.getSellerKey());

    ProductEntity product = registrationDto.toEntity(KeyGenerator.generateKey());
    product.setSeller(seller.getSellerKey());
    productRepository.save(product);

    log.info(
        "Seller : " + seller.getSellerKey() + " register this product : "
            + product.getProductKey());

    return ProductDto.fromEntity(product);
  }

  /**
   * 상품 수정 : 상품 키를 통해 상품 정보를 찾은 후 수정
   *
   * @param productEditDto
   * @return
   */
  @Transactional
  public ProductDto edit(ProductEditDto productEditDto) {
    ProductEntity product = findByProductKeyOrThrow(productEditDto.getProductKey());

    product.updateProduct(productEditDto);

    log.info("Seller : " + product.getSellerKey() + " edited this product's information : "
        + product.getProductKey());

    return ProductDto.fromEntity(product);
  }

  /**
   * 상품 삭제 : 판매자 본인인지 확인
   *
   * @param productKey
   * @param token
   * @return
   */
  @Transactional
  public String delete(String productKey, String token) {
    validateAuthorizationAndGetSeller(productKey, token);

    productRepository.deleteByProductKey(productKey);

    log.info("Product is deleted : " + productKey);

    return productKey;
  }

  /**
   * 상품 키를 통해 상품 엔티티 찾기
   *
   * @param productKey
   * @return
   */
  private ProductEntity findByProductKeyOrThrow(String productKey) {
    return productRepository.findByProductKey(productKey)
        .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND));
  }

  /**
   * 상품키를 통해 해당 판매자가 상품을 등록한 본인인지 확인
   *
   * @param productKey
   * @param email
   * @return
   */
  public ProductDto validateAuthorizationAndGetSeller(String productKey, String email) {
    ProductEntity product = findByProductKeyOrThrow(productKey);
    String sellerKeyFromProductKey = product.getSellerKey();

    String sellerKeyFromEmail = sellerService.findByEmail(email).getSellerKey();

    if (!sellerKeyFromProductKey.equals(sellerKeyFromEmail)) {
      throw new CommonCustomException(INVALID_REQUEST);
    }

    return ProductDto.fromEntity(product);
  }
}
