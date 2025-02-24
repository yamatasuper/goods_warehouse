package com.goods.product.task5;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query("SELECT c.login AS key, c.accountNumber AS value FROM Customer c WHERE c.login IN :logins")
  Map<String, String> findAccountNumbersByLogins(@Param("logins") List<String> logins);

  @Query("SELECT c.login AS key, c.inn AS value FROM Customer c WHERE c.login IN :logins")
  Map<String, String> findInnNumbersByLogins(@Param("logins") List<String> logins);

  // Получаем список кастомеров по логинам
  List<Customer> findByLoginIn(List<String> logins);
}
