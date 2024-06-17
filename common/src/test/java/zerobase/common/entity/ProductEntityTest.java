package zerobase.common.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static zerobase.common.type.Category.CLOTHES;

import jakarta.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import zerobase.common.exception.CommonCustomException;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductEntityTest {

  @Autowired
  private TestEntityManager entityManager;

  /**
   * 상품 등록 테스트
   */
  @Test
  public void testProductEntityMapping() {
    // Given
    LocalDateTime now = LocalDateTime.now();

    ProductEntity product = ProductEntity.builder()
        .productKey("P12345")
        .name("상품 12345")
        .category(CLOTHES)
        .price(10000L)
        .stock(50L)
        .description("상품 12345의 설명")
        .build();

    product.setSeller("S12345");

    // When
    entityManager.persist(product);
    entityManager.flush();
    entityManager.clear();

    // Then
    ProductEntity foundProduct = entityManager.find(ProductEntity.class, product.getId());
    assertEquals("P12345", foundProduct.getProductKey());
    assertEquals("상품 12345", foundProduct.getName());
    assertEquals(CLOTHES, foundProduct.getCategory());
    assertEquals(10000L, foundProduct.getPrice());
    assertEquals(50L, foundProduct.getStock());
    assertEquals("상품 12345의 설명", foundProduct.getDescription());
    assertEquals("S12345", foundProduct.getSellerKey());
    assertNotNull(foundProduct.getCreatedAt());
    assertEquals(now.toLocalDate(), foundProduct.getCreatedAt().toLocalDate());
  }

  /**
   * 상품의 재고 감소 + update 시 modifiedAt 변경 여부 테스트
   */
  @Test
  public void testDecreaseStock() {
    // Given
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    ProductEntity product = ProductEntity.builder()
        .productKey("P12345")
        .name("상품 12345")
        .category(CLOTHES)
        .price(10000L)
        .stock(50L)
        .description("상품 12345의 설명")
        .build();
    product.setSeller("S12345");

    entityManager.persistAndFlush(product);

    // When
    product.decreaseStock(10L);
    entityManager.flush();

    // Then
    Optional<ProductEntity> optionalProduct = Optional.ofNullable(
        entityManager.find(ProductEntity.class, product.getId()));
    ProductEntity updatedProduct = optionalProduct.orElse(null);
    assertNotNull(updatedProduct);
    assertEquals(40L, updatedProduct.getStock());
    assertNotNull(updatedProduct.getModifiedAt());
    assertEquals(now.truncatedTo(ChronoUnit.SECONDS),
        updatedProduct.getModifiedAt().truncatedTo(ChronoUnit.SECONDS));
  }

  /**
   * 상품 재고 감소 시 파라미터로 음수가 들어왔을 경우
   */
  @Test
  public void testNegativeStockDecrease() {
    // Given
    ProductEntity product = ProductEntity.builder()
        .productKey("P12345")
        .name("상품 12345")
        .category(CLOTHES)
        .price(10000L)
        .stock(50L)
        .description("상품 12345의 설명")
        .build();
    product.setSeller("S12345");

    entityManager.persistAndFlush(product);

    // When
    Exception exception = assertThrows(CommonCustomException.class, () -> {
      product.decreaseStock(-10L);
    });

    // Then
    assertEquals("상품의 재고는 음수가 될 수 없습니다.", exception.getMessage());
  }

  /**
   * 상품 재고 감소 시 파라미터로 들어온 수가 재고보다 클 경우
   */
  @Test
  public void testInsufficientStockDecrease() {
    // Given
    ProductEntity product = ProductEntity.builder()
        .productKey("P12345")
        .name("상품 12345")
        .category(CLOTHES)
        .price(10000L)
        .stock(50L)
        .description("상품 12345의 설명")
        .build();
    product.setSeller("S12345");

    entityManager.persistAndFlush(product);

    // When
    Exception exception = assertThrows(CommonCustomException.class, () -> {
      product.decreaseStock(60L);
    });

    // Then
    assertEquals("상품의 재고가 부족합니다.", exception.getMessage());
  }

  /**
   * Null 확인 테스트
   */
  @Test
  public void testNullFieldsValidation() {
    // Given
    ProductEntity product = ProductEntity.builder()
        .productKey(null)
        .name(null)
        .category(null)
        .price(null)
        .stock(null)
        .description(null)
        .build();
    product.setSeller(null);

    // When & Then
    assertThrows(PersistenceException.class, () -> {
      entityManager.persistAndFlush(product);
    });
  }
}