package com.goods.product.cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByCartId(UUID cartId);

  Optional<CartItem> findByCartIdAndProductId(UUID cartId, Long productId);
}
