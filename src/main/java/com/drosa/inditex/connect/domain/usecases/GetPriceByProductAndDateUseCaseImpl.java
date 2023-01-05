package com.drosa.inditex.connect.domain.usecases;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceInvalidParametersException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import com.drosa.inditex.connect.domain.helpers.PriceProductValidator;
import com.drosa.inditex.connect.domain.repositories.PriceProductRepository;

import java.time.LocalDateTime;

import com.drosa.inditex.connect.domain.repositories.stereotypes.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class GetPriceByProductAndDateUseCaseImpl implements GetPriceByProductAndDateUseCase {

  private final PriceProductRepository priceProductRepository;

  private final PriceProductValidator priceProductValidator;

  @Override
  public ProductPrice getPriceByProductAndDate(final int brandId, final int productId, final LocalDateTime date)
      throws InditexConnectServicePriceNotFoundException, InditexConnectServiceInvalidParametersException {

    if (!priceProductValidator.validateParameters(brandId, productId)) {
      throw new InditexConnectServiceInvalidParametersException(
          String.format("Invalid parameters for brandId <%d>, productId <%d>, and date <%s>", brandId, productId, date));
    }

    final ProductPrice productPrice = priceProductRepository.get(brandId, productId, date);
    if (productPrice == null) {
      throw new InditexConnectServicePriceNotFoundException(
          String.format("Price not found for brandId <%d>, productId <%d>, and date <%s>", brandId, productId, date));
    }

    return productPrice;
  }
}
