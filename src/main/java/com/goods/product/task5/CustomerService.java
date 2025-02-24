package com.goods.product.task5;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired private CustomerRepository customerRepository;

  // Асинхронный метод для получения мапы логин - номер счета
  @Async
  public CompletableFuture<Map<String, String>> getAccountNumbersByLogins(List<String> logins) {
    List<Customer> customers = customerRepository.findByLoginIn(logins);
    Map<String, String> accountNumbers =
        customers.stream()
            .collect(Collectors.toMap(Customer::getLogin, Customer::getAccountNumber));
    return CompletableFuture.completedFuture(accountNumbers);
  }

  // Асинхронный метод для получения мапы логин - ИНН
  @Async
  public CompletableFuture<Map<String, String>> getInnNumbersByLogins(List<String> logins) {
    List<Customer> customers = customerRepository.findByLoginIn(logins);
    Map<String, String> innNumbers =
        customers.stream().collect(Collectors.toMap(Customer::getLogin, Customer::getInn));
    return CompletableFuture.completedFuture(innNumbers);
  }
}
