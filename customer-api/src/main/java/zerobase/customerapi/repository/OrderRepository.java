package zerobase.customerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.customerapi.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
