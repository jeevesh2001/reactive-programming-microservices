package com.mishra.productservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {
  @Id
  private String id;
  private String description;
  private String price;

}
