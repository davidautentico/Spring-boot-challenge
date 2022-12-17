package com.drosa.inditex.connect.usecases;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceInvalidParametersException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import com.drosa.inditex.connect.domain.helpers.PriceProductValidator;
import com.drosa.inditex.connect.domain.repositories.PriceProductRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetPriceByProductAndDateUseCaseImpl implements GetPriceByProductAndDateUseCase {

  private final PriceProductRepository priceProductRepository;

  private final PriceProductValidator priceProductValidator;

  
  @Override
  public ProductPrice getPriceByProductAndDate(int brandId, int productId, LocalDateTime date)
      throws InditexConnectServicePriceNotFoundException, InditexConnectServiceInvalidParametersException {

    if (!priceProductValidator.validateParameters(brandId, productId)){
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
