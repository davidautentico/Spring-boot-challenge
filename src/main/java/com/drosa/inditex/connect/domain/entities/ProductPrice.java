package com.drosa.inditex.connect.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ProductPrice {

  private int productId;

  private int brandId;

  private int tariffRate;

  private int priority;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private BigDecimal price;

  private LocalDateTime lastUpdateDate;

  private String lastUpdateBy;

}
