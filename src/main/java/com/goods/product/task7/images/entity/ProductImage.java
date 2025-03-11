package com.goods.product.task7.images.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "product_image")
public class ProductImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @Column(name = "s3_key", nullable = false)
  private String s3Key;
}
