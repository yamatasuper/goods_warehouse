// package com.goods.product.task3.microservice;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.math.BigDecimal;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.HashMap;
// import java.util.Map;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.cache.Cache;
// import org.springframework.cache.CacheManager;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;
//
// @Service
// public class CurrencyCacheService {
//
//  private final CacheManager cacheManager;
//  private final RestTemplate restTemplate;
//  private final ObjectMapper objectMapper;
//
//  @Value("${currency-service.host}")
//  private String currencyServiceHost;
//
//  @Value("${currency-service.methods.get-currency}")
//  private String getCurrencyMethod;
//
//  public CurrencyCacheService(
//      CacheManager cacheManager, RestTemplate restTemplate, ObjectMapper objectMapper) {
//    this.cacheManager = cacheManager;
//    this.restTemplate = restTemplate;
//    this.objectMapper = objectMapper;
//  }
//
//  public Map<String, BigDecimal> getCachedExchangeRates() {
//    // Получаем кэш
//    Cache cache = cacheManager.getCache("currencyRates");
//    // Проверяем, есть ли данные в кэше
//    Map<String, BigDecimal> rates = cache != null ? cache.get("rates", Map.class) : null;
//
//    if (rates == null) {
//      // Если данных нет в кэше, загружаем их из сервиса
//      rates = fetchRatesFromService();
//      // Сохраняем полученные данные в кэш
//      if (cache != null && rates != null) {
//        cache.put("rates", rates);
//      }
//    }
//    return rates;
//  }
//
//  private Map<String, BigDecimal> fetchRatesFromService() {
//    Map<String, BigDecimal> rates = new HashMap<>();
//
//    try {
//      // Попытка получить курсы через RestTemplate
//      ResponseEntity<Map> response =
//          restTemplate.getForEntity(currencyServiceHost + getCurrencyMethod, Map.class);
//      rates = response.getBody();
//
//      // Если успешно получены курсы
//      if (rates != null && !rates.isEmpty()) {
//        return rates;
//      }
//    } catch (Exception e) {
//      System.err.println("Failed to fetch rates from service: " + e.getMessage());
//    }
//
//    // Если не получилось получить из сервиса, пробуем из файла
//    return getRatesFromJsonFile();
//  }
//
//  private Map<String, BigDecimal> getRatesFromJsonFile() {
//    Map<String, BigDecimal> rates = new HashMap<>();
//    try {
//      // Чтение данных из JSON-файла
//      String json =
//          new String(Files.readAllBytes(Paths.get("src/main/resources/exchange-rate.json")));
//      rates = objectMapper.readValue(json, Map.class);
//
//      if (rates != null && !rates.isEmpty()) {
//        return rates;
//      } else {
//        System.err.println("No rates found in the file.");
//      }
//    } catch (Exception e) {
//      System.err.println("Failed to read rates from file: " + e.getMessage());
//    }
//
//    return rates; // Возвращаем пустую карту, если не удалось получить данные
//  }
// }
