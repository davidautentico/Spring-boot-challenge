package com.drosa.inditex.connect.api.rest.controller;

import com.drosa.inditex.connect.api.rest.dtos.ProductPriceDto;
import com.drosa.inditex.connect.api.rest.mappers.ProductPriceMapper;
import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceRepositoryException;
import com.drosa.inditex.connect.usecases.GetPriceByProductAndDateUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inditex")
@RequiredArgsConstructor
@Slf4j
public class PriceController {

  private final ProductPriceMapper productPriceMapper;

  private final GetPriceByProductAndDateUseCase getPriceByProductAndDateUseCase;

  @PreAuthorize("permitAll()")
  @GetMapping("/price")
  public ResponseEntity<ProductPriceDto> getProductPrice(
      @RequestParam("priceDate") @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime priceDate,
      int productId, int brandId) throws InditexConnectServicePriceNotFoundException, InditexConnectServiceRepositoryException {

    log.info("Request for product tariff received, productId <{}>, brandId<{}>, date <{}>", productId, brandId, priceDate);

    final ProductPrice productPrice = getPriceByProductAndDateUseCase.getPriceByProductAndDate(brandId, productId, priceDate);

    final ProductPriceDto productPriceDto = productPriceMapper.productPriceToDto(productPrice);

    return new ResponseEntity<>(productPriceDto, HttpStatus.OK);
  }
}
