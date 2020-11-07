package com.reactive.pluralsight.service;

import com.reactive.pluralsight.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> findAll();

    Mono<Product> findById(String id);

    Mono<Void> deleteById(String id);

    Mono<Void> deleteAll();

    Mono<Product> update(String id, Product product);

    Mono<Product> save(Product product);
}
