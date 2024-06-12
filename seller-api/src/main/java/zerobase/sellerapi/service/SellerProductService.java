package zerobase.sellerapi.service;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;
import static zerobase.common.exception.CommonErrorCode.PRODUCT_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.dto.product.EditDto;
import zerobase.common.dto.product.ProductDto;
import zerobase.common.dto.product.RegistrationDto;
import zerobase.common.entity.ProductEntity;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.repository.ProductRepository;
import zerobase.common.util.KeyGenerator;
import zerobase.sellerapi.entity.SellerEntity;
import zerobase.sellerapi.security.SellerTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerProductService {

  private final ProductRepository productRepository;
  private final SellerService sellerService;
  private final SellerTokenProvider sellerTokenProvider;

  /**
   * 매장 등록 : 판매자 키를 통해 판매자 정보를 찾은 후 매장 등록
   */
  @Transactional
  public ProductDto registration(RegistrationDto registrationDto) {
    SellerEntity seller = sellerService.findBySellerKeyOrThrow(
        registrationDto.getSellerKey());

    ProductEntity product = registrationDto.toEntity(KeyGenerator.generateKey());
    product.setSeller(seller.getSellerKey());
    productRepository.save(product);

    return ProductDto.fromEntity(product);
  }

  @Transactional
  public ProductDto edit(EditDto editDto) {
    ProductEntity product = findByProductKeyOrThrow(editDto.getProductKey());

    product.updateProduct(editDto);

    return ProductDto.fromEntity(product);
  }

  private ProductEntity findByProductKeyOrThrow(String productKey) {
    return productRepository.findByProductKey(productKey)
        .orElseThrow(() -> new CommonCustomException(PRODUCT_NOT_FOUND));
  }

  public ProductDto validateAuthorizationAndGetSeller(String productKey, String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = sellerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    ProductEntity product = findByProductKeyOrThrow(productKey);
    String sellerKeyFromProductKey = product.getSellerKey();

    String sellerKeyFromEmail = sellerService.findByEmail(email).getSellerKey();

    if (!sellerKeyFromProductKey.equals(sellerKeyFromEmail)) {
      throw new CommonCustomException(INVALID_REQUEST);
    }

    return ProductDto.fromEntity(product);
  }

  @Transactional
  public String delete(String productKey, String token) {
    validateAuthorizationAndGetSeller(productKey, token);

    productRepository.deleteByProductKey(productKey);

    return productKey;
  }
}
