package com.drosa.inditex.connect.api.rest.controller;

import com.drosa.inditex.connect.api.rest.dtos.ProductPriceDto;
import com.drosa.inditex.connect.api.rest.mappers.ProductPriceMapper;
import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceInvalidParametersException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import com.drosa.inditex.connect.domain.usecases.GetPriceByProductAndDateUseCase;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
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
@RequestMapping("/inditex/api")
@RequiredArgsConstructor
@Slf4j
public class PriceController {

  private static final String MESSAGE_INTERNAL_ERROR = "Internal server error";

  private static final String MESSAGE_INVALID_PARAMETERS = "Invalid parameters";

  private static final String MESSAGE_PRICE_NOT_FOUND = "Price not found";

  private static final String MESSAGE_PRICE_FOUND = "Price found";

  private final ProductPriceMapper productPriceMapper;

  private final GetPriceByProductAndDateUseCase getPriceByProductAndDateUseCase;

  @ApiOperation(value = "Get the product price given a productId, a brandId and a date")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = MESSAGE_PRICE_FOUND,
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProductPriceDto.class)) }),
      @ApiResponse(responseCode = "422", description = MESSAGE_INVALID_PARAMETERS,
          content = @Content),
      @ApiResponse(responseCode = "404", description = MESSAGE_PRICE_NOT_FOUND,
          content = @Content),
      @ApiResponse(responseCode = "500", description = MESSAGE_INTERNAL_ERROR,
          content = @Content)}
  )
  @PreAuthorize("permitAll()")
  @GetMapping("/price")
  public ResponseEntity<ProductPriceDto> getProductPrice(
      @ApiParam(required = true) @RequestParam("priceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime priceDate,
      @ApiParam(required = true) @Min(1) int productId,
      @ApiParam(required = true) @Min(1) int brandId)
      throws InditexConnectServicePriceNotFoundException, InditexConnectServiceInvalidParametersException {

    log.info("Request for product tariff received, productId <{}>, brandId<{}>, date <{}>", productId, brandId, priceDate);

    final ProductPrice productPrice = getPriceByProductAndDateUseCase.getPriceByProductAndDate(brandId, productId, priceDate);

    final ProductPriceDto productPriceDto = productPriceMapper.productPriceToDto(productPrice);

    return new ResponseEntity<>(productPriceDto, HttpStatus.OK);
  }
}
