package com.mishra.userservice.service;

import com.mishra.userservice.dto.TransactionRequestDto;
import com.mishra.userservice.dto.TransactionResponseDto;
import com.mishra.userservice.dto.TransactionStatus;
import com.mishra.userservice.entity.UserTransaction;
import com.mishra.userservice.repository.UserRepository;
import com.mishra.userservice.repository.UserTransactionRepository;
import com.mishra.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserTransactionRepository transactionRepository;

  public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
    return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
        .filter(Boolean::booleanValue)
        .map(b -> EntityDtoUtil.toEntity(requestDto))
        .flatMap(this.transactionRepository::save)
        .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
        .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
  }

  public Flux<UserTransaction> getByUserId(int userId) {
    return this.transactionRepository.findByUserId(userId);
  }
}
