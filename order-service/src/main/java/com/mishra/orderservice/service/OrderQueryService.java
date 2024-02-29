package com.mishra.orderservice.service;

import com.mishra.orderservice.dto.PurchaseOrderResponseDto;
import com.mishra.orderservice.repository.PurchaseOrderRepository;
import com.mishra.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


@Service
public class OrderQueryService {

  @Autowired
  private PurchaseOrderRepository orderRepository;

  public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
    return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream())
        .map(EntityDtoUtil::getPurchaseOrderResponseDto)
        .subscribeOn(Schedulers.boundedElastic());
  }
}
