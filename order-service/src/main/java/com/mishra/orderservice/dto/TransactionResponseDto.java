package com.mishra.orderservice.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {
  private Integer userId;
  private Integer amount;
  private TransactionStatus status;
}
