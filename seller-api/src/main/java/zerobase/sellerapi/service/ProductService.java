package zerobase.sellerapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.util.KeyGenerator;
import zerobase.sellerapi.dto.product.ProductDto;
import zerobase.sellerapi.dto.product.RegistrationDto;
import zerobase.sellerapi.entity.ProductEntity;
import zerobase.sellerapi.entity.SellerEntity;
import zerobase.sellerapi.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final SellerService sellerService;

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
}
