package com.goods.product.task3.microservice;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder()
        .baseUrl("http://localhost:8081")
        .filter(
            ExchangeFilterFunction.ofRequestProcessor(
                request -> {
                  return Mono.just(request);
                }))
        .clientConnector(
            new ReactorClientHttpConnector(
                HttpClient.create()
                    .doOnConnected(
                        conn ->
                            conn.addHandlerLast(new io.netty.handler.timeout.ReadTimeoutHandler(10))
                                .addHandlerLast(
                                    new io.netty.handler.timeout.WriteTimeoutHandler(10)))))
        .filter(
            (request, next) ->
                next.exchange(request)
                    .retryWhen(
                        Retry.fixedDelay(
                            3, Duration.ofSeconds(2))) // retry 3 times with 2 seconds delay
            );
  }
}
