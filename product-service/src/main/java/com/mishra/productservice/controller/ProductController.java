package com.mishra.productservice.controller;

import com.mishra.productservice.dto.ProductDto;
import com.mishra.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping("all")
  public Flux<ProductDto> getAll(){
    return productService.getAll();
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id){
    return productService.getProductById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono){
    return productService.insertProduct(productDtoMono);
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono){
      return this.productService.updateProduct(id, productDtoMono)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public Mono<Void> deleteProduct(@PathVariable String id) {
    return this.productService.deleteProduct(id);
  }

//  @GetMapping("price-range")
//  Flux<ResponseEntity<ProductDto>> getProductInRange(@RequestParam("min") String min, @RequestParam("max") String max) {
//    return this.productService.getProductInRange(Integer.parseInt(min), Integer.parseInt(max))
//        .map(ResponseEntity::ok)
//        .defaultIfEmpty(ResponseEntity.notFound().build());
//  }

  @GetMapping("price-range")
  public Flux<ProductDto> getProductInRange(@RequestParam("min") int min, @RequestParam("max") int max) {
    return this.productService.getProductInRange(min, max);
  }
}
