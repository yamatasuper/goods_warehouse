package com.goods.product.task5;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired private CustomerService customerService;

  @PostMapping("/getAccountNumbers")
  public CompletableFuture<Map<String, String>> getAccountNumbers(
      @RequestBody List<String> logins) {
    return customerService.getAccountNumbersByLogins(logins);
  }

  @PostMapping("/getInnNumbers")
  public CompletableFuture<Map<String, String>> getInnNumbers(@RequestBody List<String> logins) {
    return customerService.getInnNumbersByLogins(logins);
  }
}
