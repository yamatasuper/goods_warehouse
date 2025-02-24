package com.goods.product.task5;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String login;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private Boolean isActive;

  @Column(unique = true, nullable = false)
  private String accountNumber;

  @Column(unique = true, nullable = false)
  private String inn;
}
