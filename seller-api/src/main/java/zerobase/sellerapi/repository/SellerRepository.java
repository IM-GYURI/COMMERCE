package zerobase.sellerapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.sellerapi.entity.SellerEntity;


public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

  boolean existsByEmail(String email);

  boolean existsBySellerKey(String sellerKey);

  Optional<SellerEntity> findByEmail(String email);

  Optional<SellerEntity> findBySellerKey(String sellerKey);

  void deleteBySellerKey(String sellerKey);
}
