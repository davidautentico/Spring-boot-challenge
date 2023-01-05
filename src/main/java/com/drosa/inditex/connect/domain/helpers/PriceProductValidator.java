package com.drosa.inditex.connect.domain.helpers;

import org.springframework.stereotype.Component;

@Component
public class PriceProductValidator {

  public boolean validateParameters(final int brandId, final int productId) {
    return brandId > 0 && productId > 0;
  }
}
