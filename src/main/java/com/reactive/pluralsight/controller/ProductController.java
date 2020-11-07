package com.reactive.pluralsight.controller;

import com.reactive.pluralsight.model.Product;
import com.reactive.pluralsight.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productsController")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<Product> getAllProducts() {
        return this.productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable("id") String id) {
        return this.productService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return this.productService.deleteById(id);
    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return this.productService.deleteAll();
    }

    @PutMapping("/{id}")
    public Mono<Product> update(@PathVariable("id") String id, @RequestBody Product product) {
        return this.productService.update(id, product);
    }

    @PostMapping
    public Mono<Product> save(@RequestBody Product product) {
        return this.productService.save(product);
    }
}
