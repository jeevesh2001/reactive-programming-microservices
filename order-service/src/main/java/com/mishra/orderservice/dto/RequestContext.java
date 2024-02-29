package com.mishra.orderservice.dto;

import lombok.Data;

@Data
public class RequestContext {
  private PurchaseOrderRequestDto purchaseOrderRequestDto;
  private ProductDto productDto;
  private TransactionRequestDto transactionRequestDto;
  private TransactionResponseDto transactionResponseDto;

  public RequestContext(PurchaseOrderRequestDto requestDto){
    this.purchaseOrderRequestDto = requestDto;
  }
}
