package com.goods.product.task4.repository;

import com.goods.product.task4.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
  Optional<Order> findByIdAndCustomerId(UUID orderId, Long customerId);

  @Query(
      "SELECT o FROM Order o JOIN o.items oi JOIN oi.product p WHERE o.status IN ('CREATED', 'CONFIRMED') AND p.id IN :productIds")
  List<Order> findOrdersByProductIds(@Param("productIds") List<Long> productIds);
}
