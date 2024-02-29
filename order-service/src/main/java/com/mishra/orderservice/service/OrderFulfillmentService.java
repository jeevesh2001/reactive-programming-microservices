package com.mishra.orderservice.service;

import com.mishra.orderservice.client.ProductClient;
import com.mishra.orderservice.client.UserClient;
import com.mishra.orderservice.dto.PurchaseOrderRequestDto;
import com.mishra.orderservice.dto.PurchaseOrderResponseDto;
import com.mishra.orderservice.dto.RequestContext;
import com.mishra.orderservice.repository.PurchaseOrderRepository;
import com.mishra.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {
  @Autowired
  private ProductClient productClient;

  @Autowired
  private UserClient userClient;

  @Autowired
  private PurchaseOrderRepository purchaseOrderRepository;

  public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
    return requestDtoMono.map(RequestContext::new)
        .flatMap(this::productRequestResponse)
        .doOnNext(EntityDtoUtil::setTransactionRequestDto)
        .flatMap(this::userRequestResponse)
        .map(EntityDtoUtil::getPurchaseOrder)
        .map(purchaseOrderRepository::save) // blocking
        .map(EntityDtoUtil::getPurchaseOrderResponseDto)
        .subscribeOn(Schedulers.boundedElastic())
        .doOnError(e -> System.out.println(e.getMessage()));
  }

  private Mono<RequestContext> productRequestResponse(RequestContext rc) {
    return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
        .doOnNext(rc::setProductDto)
        .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
        .thenReturn(rc);
  }

  private Mono<RequestContext> userRequestResponse(RequestContext rc) {
    return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
        .doOnNext(rc::setTransactionResponseDto)
        .thenReturn(rc);
  }
}
