package com.goods.product.cart;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;

  public CartService(
      CartRepository cartRepository,
      CartItemRepository cartItemRepository,
      ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
    this.productRepository = productRepository;
  }

  @Transactional(readOnly = true)
  public CartDto getCart(Long customerId) {
    Cart cart =
        cartRepository.findByCustomerId(customerId).orElseGet(() -> createNewCart(customerId));

    return mapToDto(cart);
  }

  @Transactional
  public CartDto addItemToCart(Long customerId, CartItemRequest request) {
    Cart cart =
        cartRepository.findByCustomerId(customerId).orElseGet(() -> createNewCart(customerId));

    Product product =
        productRepository
            .findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

    // Check if item already exists in cart
    cartItemRepository
        .findByCartIdAndProductId(cart.getId(), product.getId())
        .ifPresentOrElse(
            item -> {
              item.setQuantity(item.getQuantity() + request.getQuantity());
              cartItemRepository.save(item);
            },
            () -> {
              CartItem newItem = new CartItem();
              newItem.setCart(cart);
              newItem.setProduct(product);
              newItem.setQuantity(request.getQuantity());
              cartItemRepository.save(newItem);
            });

    return mapToDto(cartRepository.save(cart));
  }

  @Transactional
  public CartDto updateItemQuantity(Long customerId, Long productId, int quantity) {
    Cart cart =
        cartRepository
            .findByCustomerId(customerId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    CartItem item =
        cartItemRepository
            .findByCartIdAndProductId(cart.getId(), productId)
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));

    if (quantity <= 0) {
      cartItemRepository.delete(item);
    } else {
      item.setQuantity(quantity);
      cartItemRepository.save(item);
    }

    return mapToDto(cartRepository.save(cart));
  }

  @Transactional
  public void removeItemFromCart(Long customerId, Long productId) {
    Cart cart =
        cartRepository
            .findByCustomerId(customerId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    CartItem item =
        cartItemRepository
            .findByCartIdAndProductId(cart.getId(), productId)
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));

    cartItemRepository.delete(item);
  }

  private Cart createNewCart(Long customerId) {
    Cart newCart = new Cart();
    newCart.setCustomerId(customerId);
    return cartRepository.save(newCart);
  }

  private CartDto mapToDto(Cart cart) {
    List<CartItemDto> itemDtos =
        cartItemRepository.findByCartId(cart.getId()).stream()
            .map(this::mapItemToDto)
            .collect(Collectors.toList());

    BigDecimal totalPrice =
        itemDtos.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    CartDto dto = new CartDto();
    dto.setId(cart.getId());
    dto.setCustomerId(cart.getCustomerId());
    dto.setItems(itemDtos);
    dto.setTotalPrice(totalPrice);

    return dto;
  }

  private CartItemDto mapItemToDto(CartItem item) {
    CartItemDto dto = new CartItemDto();
    dto.setId(item.getId());
    dto.setProductId(item.getProduct().getId());
    dto.setProductName(item.getProduct().getName());
    dto.setPrice(item.getProduct().getPrice());
    dto.setCurrency(item.getProduct().getCurrency());
    dto.setQuantity(item.getQuantity());
    dto.setImageUrl(item.getProduct().getName());

    return dto;
  }
}
