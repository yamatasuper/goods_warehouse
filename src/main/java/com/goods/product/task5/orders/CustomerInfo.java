package com.goods.product.task5.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInfo {
  private Long id;
  private String accountNumber;
  private String email;
  private String inn;

  // Добавляем конструктор с параметрами
  public CustomerInfo(Long id, String accountNumber, String email, String inn) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.email = email;
    this.inn = inn;
  }
}
