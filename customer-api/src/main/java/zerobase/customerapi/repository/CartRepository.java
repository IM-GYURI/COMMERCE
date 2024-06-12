package zerobase.customerapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.customerapi.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

  boolean existsByCustomerKey(String customerKey);

  Optional<CartEntity> findByCustomerKey(String customerKey);
}
