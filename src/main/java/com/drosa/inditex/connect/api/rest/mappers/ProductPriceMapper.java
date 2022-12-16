package com.drosa.inditex.connect.api.rest.mappers;

import com.drosa.inditex.connect.api.rest.dtos.ProductPriceDto;
import com.drosa.inditex.connect.domain.entities.ProductPrice;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface ProductPriceMapper {

  @Mappings({@Mapping(source = "productId", target = "productId"),
      @Mapping(source = "brandId", target = "brandId"),
      @Mapping(source = "tariffRate", target = "tariffRate"),
      @Mapping(source = "startDate", target = "startDate"),
      @Mapping(source = "endDate", target = "endDate"),
      @Mapping(source = "price", target = "price")})
  ProductPriceDto productPriceToDto(final ProductPrice productPrice);
}
