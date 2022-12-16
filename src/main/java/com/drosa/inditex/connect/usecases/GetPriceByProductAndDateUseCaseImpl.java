package com.drosa.inditex.connect.usecases;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceRepositoryException;
import com.drosa.inditex.connect.infrastructure.repositories.PriceProductRepositoryImpl;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetPriceByProductAndDateUseCaseImpl implements GetPriceByProductAndDateUseCase {

  private final PriceProductRepositoryImpl priceProductRepositoryImpl;

  @Override
  public ProductPrice getPriceByProductAndDate(int brandId, int productId, LocalDateTime date)
      throws InditexConnectServicePriceNotFoundException, InditexConnectServiceRepositoryException {

    try {
      final ProductPrice productPrice = priceProductRepositoryImpl.get(brandId, productId, date);
      if (productPrice == null) {
        throw new InditexConnectServicePriceNotFoundException(
            String.format("Price not found for bradId <%d>, productId <%d>, and date <%s>", brandId, productId, date));
      }

      return productPrice;
    } catch (Exception e) {
      throw new InditexConnectServiceRepositoryException(
          String.format("Error obtaining price from repository for bradId <%d>, productId <%d>, and date <%s>", brandId, productId, date));
    }
  }
}
