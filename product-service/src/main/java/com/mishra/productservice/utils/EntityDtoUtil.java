package com.mishra.productservice.utils;

import com.mishra.productservice.dto.ProductDto;
import com.mishra.productservice.entity.Product;
import org.modelmapper.ModelMapper;

public class EntityDtoUtil {

  private static final ModelMapper mapper = new ModelMapper();
  public static ProductDto toDto(Product product) {
    return mapper.map(product, ProductDto.class);
  }

  public static Product toEntity(ProductDto dto) {
    return mapper.map(dto, Product.class);

  }
}
