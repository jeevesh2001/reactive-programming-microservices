package com.mishra.orderservice.util;

import com.mishra.orderservice.dto.*;
import com.mishra.orderservice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

  public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
    PurchaseOrderResponseDto responseDto = new PurchaseOrderResponseDto();
    BeanUtils.copyProperties(purchaseOrder, responseDto);
    responseDto.setOrderId(purchaseOrder.getId());
    return responseDto;
  }
  public static void setTransactionRequestDto(RequestContext requestContext) {
    TransactionRequestDto dto = new TransactionRequestDto();
    dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
    dto.setAmount(Integer.valueOf(requestContext.getProductDto().getPrice()));
    requestContext.setTransactionRequestDto(dto);
  }

  public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
    PurchaseOrder purchaseOrder = new PurchaseOrder();
    purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
    purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
    purchaseOrder.setAmount(Integer.valueOf(requestContext.getProductDto().getPrice()));

    TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
    OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
    purchaseOrder.setStatus(orderStatus);
    return purchaseOrder;
  }
}
