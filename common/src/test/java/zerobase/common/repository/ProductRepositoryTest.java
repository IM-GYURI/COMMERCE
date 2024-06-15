package zerobase.common.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static zerobase.common.type.Category.BOOK;
import static zerobase.common.type.Category.CLOTHES;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import zerobase.common.entity.ProductEntity;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @BeforeEach
  public void setUp() {
    productRepository.deleteAll();

    // Given
    ProductEntity product1 = ProductEntity.builder()
        .productKey("P12345")
        .name("상품 12345")
        .category(CLOTHES)
        .price(10000L)
        .stock(50L)
        .description("상품 12345의 설명")
        .build();

    product1.setSeller("S12345");

    ProductEntity product2 = ProductEntity.builder()
        .productKey("P54321")
        .name("상품 54321")
        .category(BOOK)
        .price(20000L)
        .stock(30L)
        .description("상품 54321의 설명")
        .build();
    product2.setSeller("S54321");

    productRepository.save(product1);
    productRepository.save(product2);
  }

  /**
   * 상품 키를 통해 해당 상품 찾기
   */
  @Test
  public void testFindByProductKey() {
    // When
    Optional<ProductEntity> foundProduct = productRepository.findByProductKey("P12345");

    // Then
    assertTrue(foundProduct.isPresent());
    assertEquals("상품 12345", foundProduct.get().getName());
  }

  /**
   * 상품 키를 통해 해당 상품 삭제하기
   */
  @Test
  public void testDeleteByProductKey() {
    // When
    productRepository.deleteByProductKey("P12345");
    Optional<ProductEntity> foundProduct = productRepository.findByProductKey("P12345");

    // Then
    assertTrue(foundProduct.isEmpty());
  }

  /**
   * 키워드를 통해 해당하는 상품 전체 찾기
   */
  @Test
  public void testFindAllByNameContains() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);

    // When
    Page<ProductEntity> foundProducts = productRepository.findAllByNameContains("상품", pageable);

    // Then
    assertEquals(2, foundProducts.getTotalElements());
    assertEquals("상품 12345", foundProducts.getContent().get(0).getName());
    assertEquals("상품 54321", foundProducts.getContent().get(1).getName());
  }

  /**
   * 카테고리를 통해 해당하는 상품 전체 찾기
   */
  @Test
  public void testFindAllByCategory() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);

    // When
    Page<ProductEntity> foundProducts = productRepository.findAllByCategory(CLOTHES,
        pageable);

    // Then
    assertEquals(1, foundProducts.getTotalElements());
    assertEquals("상품 12345", foundProducts.getContent().get(0).getName());
  }

  /**
   * 새로운 상품을 저장하고 찾기
   */
  @Test
  public void testSaveAndFind() {
    // Given
    ProductEntity newProduct = ProductEntity.builder()
        .productKey("P00000")
        .name("상품 00000")
        .category(BOOK)
        .price(15000L)
        .stock(40L)
        .description("상품 00000의 설명")
        .build();
    newProduct.setSeller("S00000");

    // When
    productRepository.save(newProduct);
    Optional<ProductEntity> foundProduct = productRepository.findByProductKey("P00000");

    // Then
    assertTrue(foundProduct.isPresent());
    assertEquals("상품 00000", foundProduct.get().getName());
  }
}