package zerobase.common.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.common.entity.ProductEntity;
import zerobase.common.type.Category;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByProductKey(String productKey);

  void deleteByProductKey(String productKey);

  Page<ProductEntity> findAllByNameContains(String name, Pageable pageable);

  Page<ProductEntity> findAllByCategory(Category category, Pageable pageable);
}
