package com.mishra.userservice.controller;

import com.mishra.userservice.dto.TransactionRequestDto;
import com.mishra.userservice.dto.TransactionResponseDto;
import com.mishra.userservice.entity.UserTransaction;
import com.mishra.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user/transaction")
public class UserTransactionController {
  @Autowired
  private TransactionService transactionService;

  @PostMapping
  public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
    return requestDtoMono.flatMap(transactionService::createTransaction);
  }

  @GetMapping()
  public Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId) {
    return transactionService.getByUserId(userId);
  }
}
