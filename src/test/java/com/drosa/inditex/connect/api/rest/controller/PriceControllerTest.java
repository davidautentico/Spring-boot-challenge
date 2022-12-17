package com.drosa.inditex.connect.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.drosa.inditex.connect.api.rest.dtos.ProductPriceDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = com.drosa.inditex.connect.boot.InditexConnectAdapter.class)
class PriceControllerTest {

  private static RestTemplate restTemplate = null;

  private final String TEST1_URL = "price?brandId=1&productId=35455&priceDate=2020-06-14T10:00:00Z";

  private final String TEST2_URL = "price?brandId=1&productId=35455&priceDate=2020-06-14T16:00:00Z";

  private final String TEST3_URL = "price?brandId=1&productId=35455&priceDate=2020-06-14T21:00:00Z";

  private final String TEST4_URL = "price?brandId=1&productId=35455&priceDate=2020-06-15T10:00:00Z";

  private final String TEST5_URL = "price?brandId=1&productId=35455&priceDate=2020-06-16T21:00:00Z";

  private final String TEST6_NOT_FOUND_URL = "price?brandId=2&productId=35455&priceDate=2020-06-16T21:00:00Z";

  private final String TEST6_INVALID_PARAMETERS_URL = "price?brandId=-2&productId=35455&priceDate=2020-06-16T21:00:00Z";

  private final int PRODUCT_ID = 35455;

  private final int BRAND_ID = 1;

  private final int TARIFF_ONE = 1;

  private final int TARIFF_TWO = 2;

  private final int TARIFF_THREE = 3;

  private final int TARIFF_FOUR = 4;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

  @LocalServerPort
  private int port;

  private String baseUrl = "http://localhost";

  @BeforeAll
  public static void init() {
    restTemplate = new RestTemplate();
  }

  @BeforeEach
  public void setUp() {
    baseUrl = baseUrl.concat(":").concat(port + "").concat("/inditex/api/");
  }

  @Test
  void test1_shouldWork() {
    // given
    final LocalDateTime startDate = LocalDateTime.parse("2020-06-14-00.00.00", formatter);
    final LocalDateTime endDate = LocalDateTime.parse("2020-12-31-23.59.59", formatter);

    ProductPriceDto productPriceDto = new ProductPriceDto(PRODUCT_ID, BRAND_ID, TARIFF_ONE, startDate, endDate, new BigDecimal("35.50"));

    // Call
    ProductPriceDto response = restTemplate.getForObject(baseUrl.concat(TEST1_URL), ProductPriceDto.class);

    // Verify
    assertEquals(response, productPriceDto);
  }

  @Test
  void test1_shouldFail() {
    // Given
    final LocalDateTime startDate = LocalDateTime.parse("2020-06-14-00.00.00", formatter);
    final LocalDateTime endDate = LocalDateTime.parse("2020-12-31-23.59.59", formatter);

    ProductPriceDto productPriceDto = new ProductPriceDto(PRODUCT_ID, BRAND_ID, TARIFF_ONE, startDate, endDate, new BigDecimal("37.50"));

    // Call
    ProductPriceDto response = restTemplate.getForObject(baseUrl.concat(TEST1_URL), ProductPriceDto.class);

    // Verify
    assertNotEquals(response, productPriceDto);
  }

  @Test
  void test2_shouldWork() {
    // Given
    final LocalDateTime startDate = LocalDateTime.parse("2020-06-14-15.00.00", formatter);
    final LocalDateTime endDate = LocalDateTime.parse("2020-06-14-18.30.00", formatter);

    ProductPriceDto productPriceDto = new ProductPriceDto(PRODUCT_ID, BRAND_ID, TARIFF_TWO, startDate, endDate, new BigDecimal("25.45"));

    // Call
    ProductPriceDto response = restTemplate.getForObject(baseUrl.concat(TEST2_URL), ProductPriceDto.class);

    // Verify
    assertEquals(response, productPriceDto);
  }

  @Test
  void test3_shouldWork() {
    // Given
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
    final LocalDateTime startDate = LocalDateTime.parse("2020-06-14-00.00.00", formatter);
    final LocalDateTime endDate = LocalDateTime.parse("2020-12-31-23.59.59", formatter);

    ProductPriceDto productPriceDto = new ProductPriceDto(PRODUCT_ID, BRAND_ID, TARIFF_ONE, startDate, endDate, new BigDecimal("35.50"));

    // Call
    ProductPriceDto response = restTemplate.getForObject(baseUrl.concat(TEST3_URL), ProductPriceDto.class);

    // Verify
    assertEquals(response, productPriceDto);
  }

  @Test
  void test4_shouldWork() {
    // Given
    final LocalDateTime startDate = LocalDateTime.parse("2020-06-15-00.00.00", formatter);
    final LocalDateTime endDate = LocalDateTime.parse("2020-06-15-11.00.00", formatter);

    ProductPriceDto productPriceDto = new ProductPriceDto(PRODUCT_ID, BRAND_ID, TARIFF_THREE, startDate, endDate, new BigDecimal("30.50"));

    // Call
    ProductPriceDto response = restTemplate.getForObject(baseUrl.concat(TEST4_URL), ProductPriceDto.class);

    // Verify
    assertEquals(response, productPriceDto);
  }

  @Test
  void test5_shouldWork() {
    // Given
    final LocalDateTime startDate = LocalDateTime.parse("2020-06-15-16.00.00", formatter);
    final LocalDateTime endDate = LocalDateTime.parse("2020-12-31-23.59.59", formatter);

    ProductPriceDto productPriceDto = new ProductPriceDto(PRODUCT_ID, BRAND_ID, TARIFF_FOUR, startDate, endDate, new BigDecimal("38.95"));

    // Call
    ProductPriceDto response = restTemplate.getForObject(baseUrl.concat(TEST5_URL), ProductPriceDto.class);

    // Verify
    assertEquals(response, productPriceDto);
  }

  @Test
  void test6_withNonExistingPrices_shouldFail() {
    // Call and Verify
    assertThrows(NotFound.class, () -> {
      restTemplate.getForObject(baseUrl.concat(TEST6_NOT_FOUND_URL), ProductPriceDto.class);
    });
  }

  @Test
  void test6_withInvalidParameters_shouldFail() {
    // Call and Verify
    assertThrows(UnprocessableEntity.class, () -> {
      restTemplate.getForObject(baseUrl.concat(TEST6_INVALID_PARAMETERS_URL), ProductPriceDto.class);
    });
  }
}