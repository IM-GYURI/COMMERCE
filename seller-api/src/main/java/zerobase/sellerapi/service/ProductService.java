package zerobase.sellerapi.service;

import static zerobase.common.exception.ErrorCode.INVALID_REQUEST;
import static zerobase.common.exception.ErrorCode.PRODUCT_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.exception.CustomException;
import zerobase.common.util.KeyGenerator;
import zerobase.sellerapi.dto.product.EditDto;
import zerobase.sellerapi.dto.product.ProductDto;
import zerobase.sellerapi.dto.product.RegistrationDto;
import zerobase.sellerapi.entity.ProductEntity;
import zerobase.sellerapi.entity.SellerEntity;
import zerobase.sellerapi.repository.ProductRepository;
import zerobase.sellerapi.security.TokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final SellerService sellerService;
  private final TokenProvider tokenProvider;

  /**
   * 매장 등록 : 판매자 키를 통해 판매자 정보를 찾은 후 매장 등록
   */
  @Transactional
  public ProductDto registration(RegistrationDto registrationDto) {
    SellerEntity seller = sellerService.findBySellerKeyOrThrow(
        registrationDto.getSellerKey());

    ProductEntity product = registrationDto.toEntity(KeyGenerator.generateKey());
    product.setSeller(seller);
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
        .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
  }

  public ProductDto validateAuthorizationAndGetSeller(String productKey, String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = tokenProvider.getAuthentication(token);
    String email = authentication.getName();

    ProductEntity product = findByProductKeyOrThrow(productKey);
    String sellerKeyFromProductKey = product.getSellerEntity().getSellerKey();

    String sellerKeyFromEmail = sellerService.findByEmail(email).getSellerKey();

    if (!sellerKeyFromProductKey.equals(sellerKeyFromEmail)) {
      throw new CustomException(INVALID_REQUEST);
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
