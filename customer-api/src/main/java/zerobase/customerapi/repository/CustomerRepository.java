package zerobase.customerapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.customerapi.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

  boolean existsByEmail(String email);

  boolean existsByCustomerKey(String customerKey);

  Optional<CustomerEntity> findByEmail(String email);

  Optional<CustomerEntity> findByCustomerKey(String customerKey);

  void deleteByCustomerKey(String customerKey);
}
