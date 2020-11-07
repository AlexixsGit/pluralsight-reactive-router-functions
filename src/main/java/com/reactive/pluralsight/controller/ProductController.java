package com.reactive.pluralsight.controller;

import com.reactive.pluralsight.model.Product;
import com.reactive.pluralsight.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<Product> findAll() {
        return this.productService.findAll();
    }
}
