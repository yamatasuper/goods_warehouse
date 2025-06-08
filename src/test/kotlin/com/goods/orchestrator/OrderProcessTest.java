package com.example.orchestrator;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderProcessTest {

  private static WireMockServer wireMockServer;
  private TestRestTemplate restTemplate = new TestRestTemplate();

  @BeforeAll
  public static void setup() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
    configureWireMock();
  }

  @AfterAll
  public static void tearDown() {
    wireMockServer.stop();
  }

  private static void configureWireMock() {
    wireMockServer.stubFor(
        post(urlEqualTo("/api/contract/register"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"contractId\": \"12345\"}")));

    wireMockServer.stubFor(
        post(urlEqualTo("/api/delivery/register"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"deliveryDate\": \"2023-10-01\"}")));

    wireMockServer.stubFor(
        post(urlEqualTo("/api/payment/process"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"success\": true}")));
  }

  @Test
  public void testHappyPath() {
    ResponseEntity<String> response =
        restTemplate.postForEntity(
            "http://localhost:8080/order/confirm",
            "{\"deliveryAddress\": \"123 Main St\", \"inn\": \"1234567890\", \"accountNumber\": \"987654321\", \"amount\": 100.0, \"login\": \"user1\"}",
            String.class);

    assertEquals(200, response.getStatusCodeValue());
  }
}
