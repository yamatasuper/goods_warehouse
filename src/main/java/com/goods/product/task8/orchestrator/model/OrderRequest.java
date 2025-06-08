package com.goods.product.task8.orchestrator.model;

public class OrderRequest {
  private String deliveryAddress;
  private String inn;
  private String accountNumber;
  private double amount;
  private String login;

  // Конструктор, геттеры и сеттеры

  public OrderRequest() {}

  public OrderRequest(
      String deliveryAddress, String inn, String accountNumber, double amount, String login) {
    this.deliveryAddress = deliveryAddress;
    this.inn = inn;
    this.accountNumber = accountNumber;
    this.amount = amount;
    this.login = login;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public String getInn() {
    return inn;
  }

  public void setInn(String inn) {
    this.inn = inn;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }
}
