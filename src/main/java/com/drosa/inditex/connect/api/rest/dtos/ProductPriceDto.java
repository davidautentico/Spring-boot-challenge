package com.drosa.inditex.connect.api.rest.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductPriceDto implements Serializable {

  private int productId;

  private int brandId;

  private int tariffRate;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private BigDecimal price;

  public ProductPriceDto(int productId, int brandId, int tariffRate, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price){
    this.productId = productId;
    this.brandId = brandId;
    this.tariffRate = tariffRate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
  }

  public ProductPriceDto(){}
}
