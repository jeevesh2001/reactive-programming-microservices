package com.mishra.userservice.service;

import com.mishra.userservice.dto.UserDto;
import com.mishra.userservice.repository.UserRepository;
import com.mishra.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public Flux<UserDto> getAll() {
    return this.repository.findAll()
        .map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> getUserById(final int userId) {
    return this.repository.findById(userId)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
    return userDtoMono.map(EntityDtoUtil::toEntity)
        .flatMap(this.repository::save)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> updateUser(final int id, Mono<UserDto> userDtoMono) {
    return repository.findById(id)
        .flatMap(u ->
            userDtoMono
                .map(EntityDtoUtil::toEntity)
                .doOnNext(e -> e.setId(id)))
        .flatMap(this.repository::save)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<Void> deleteUser(final int id) {
    return repository.deleteById(id);
  }

}
