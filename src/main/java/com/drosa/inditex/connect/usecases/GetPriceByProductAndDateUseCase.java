package com.drosa.inditex.connect.usecases;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceRepositoryException;
import java.time.LocalDateTime;

public interface GetPriceByProductAndDateUseCase {

  ProductPrice getPriceByProductAndDate(int brandId, int productId, LocalDateTime date)
      throws InditexConnectServicePriceNotFoundException, InditexConnectServiceRepositoryException;
}