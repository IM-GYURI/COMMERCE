package com.zerobase.commerce.repository;

import com.zerobase.commerce.entity.CustomerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

  boolean existsByEmail(String email);

  Optional<CustomerEntity> findByEmail(String email);

}
