package com.mishra.userservice.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {
  private Integer userId;
  private Integer amount;
  private TransactionStatus status;
}
