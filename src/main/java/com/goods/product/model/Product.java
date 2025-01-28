package com.goods.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
  @SequenceGenerator(name = "product_seq", sequenceName = "product_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Version private Integer version;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "sku", nullable = false)
  private String sku;

  @Column(name = "description")
  private String description;

  @Column(name = "category")
  private String category;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "last_quantity_update")
  private String lastQuantityUpdate;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}
