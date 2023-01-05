package com.drosa.inditex.connect.domain.usecases;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceInvalidParametersException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import java.time.LocalDateTime;

public interface GetPriceByProductAndDateUseCase {

  ProductPrice getPriceByProductAndDate(int brandId, int productId, LocalDateTime date)
      throws InditexConnectServicePriceNotFoundException, InditexConnectServiceInvalidParametersException;
}
