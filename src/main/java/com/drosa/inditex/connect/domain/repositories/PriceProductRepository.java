package com.drosa.inditex.connect.domain.repositories;

import com.drosa.inditex.connect.domain.entities.ProductPrice;
import java.time.LocalDateTime;

public interface PriceProductRepository {

   ProductPrice get(int brandId, int productId, LocalDateTime date);
}
