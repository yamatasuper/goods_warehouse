package com.goods.product.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {
  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<CartDto> getCart(@PathVariable Long customerId) {
    return ResponseEntity.ok(cartService.getCart(customerId));
  }

  @PostMapping("/{customerId}/items")
  public ResponseEntity<CartDto> addItemToCart(
      @PathVariable Long customerId, @RequestBody CartItemRequest request) {
    return ResponseEntity.ok(cartService.addItemToCart(customerId, request));
  }

  @PutMapping("/{customerId}/items/{productId}")
  public ResponseEntity<CartDto> updateItemQuantity(
      @PathVariable Long customerId, @PathVariable Long productId, @RequestParam int quantity) {
    return ResponseEntity.ok(cartService.updateItemQuantity(customerId, productId, quantity));
  }

  @DeleteMapping("/{customerId}/items/{productId}")
  public ResponseEntity<Void> removeItemFromCart(
      @PathVariable Long customerId, @PathVariable Long productId) {
    cartService.removeItemFromCart(customerId, productId);
    return ResponseEntity.noContent().build();
  }
}
