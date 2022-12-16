package com.drosa.inditex.connect.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ProductPrice {

  int productId;

  int brandId;

  int tariffRate;

  int priority;

  LocalDateTime startDate;

  LocalDateTime endDate;

  BigDecimal price;

  LocalDateTime lastUpdateDate;

  String lastUpdateBy;

}
