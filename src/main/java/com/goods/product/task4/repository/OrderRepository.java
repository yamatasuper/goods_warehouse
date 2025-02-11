package com.goods.product.task4.repository;

import com.goods.product.task4.model.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
  Optional<Order> findByIdAndCustomerId(UUID orderId, Long customerId);
}
