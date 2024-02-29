package com.mishra.orderservice.dto;

import lombok.Data;

@Data
public class PurchaseOrderRequestDto {
  private Integer userId;
  private String productId;
}
