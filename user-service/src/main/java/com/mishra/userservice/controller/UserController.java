package com.mishra.userservice.controller;

import com.mishra.userservice.dto.UserDto;
import com.mishra.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("all")
  public Flux<UserDto> getAllUsers() {
    return userService.getAll();
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable int id) {
    return userService.getUserById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<UserDto>> createUser(@RequestBody Mono<UserDto> userDtoMono) {
    return userService.createUser(userDtoMono)
        .map(ResponseEntity::ok);
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable int id, @RequestBody Mono<UserDto> userDtoMono) {
    return userService.updateUser(id, userDtoMono)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public Mono<Void> deleteUser(@PathVariable int id){
    return userService.deleteUser(id);
  }


}
