package zerobase.sellerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.sellerapi.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
