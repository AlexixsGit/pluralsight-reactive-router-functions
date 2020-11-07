package com.reactive.pluralsight.service;

import com.reactive.pluralsight.model.Product;
import reactor.core.publisher.Flux;

public interface ProductService {
    Flux<Product> findAll();
}
