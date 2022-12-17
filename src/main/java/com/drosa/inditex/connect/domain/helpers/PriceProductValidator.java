package com.drosa.inditex.connect.domain.helpers;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PriceProductValidator {

  public boolean validateParameters(int brandId, int productId){
    return brandId > 0 && productId > 0;
  }
}
