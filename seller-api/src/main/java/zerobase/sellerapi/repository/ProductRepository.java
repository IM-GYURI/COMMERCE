package zerobase.sellerapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.sellerapi.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByProductKey(String productKey);

  void deleteByProductKey(String productKey);
}
