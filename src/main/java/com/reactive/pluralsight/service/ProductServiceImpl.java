package com.reactive.pluralsight.service;

import com.reactive.pluralsight.model.Product;
import com.reactive.pluralsight.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(String id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return this.productRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.productRepository.deleteAll();
    }

    @Override
    public Mono<Product> update(String id, Product product) {

        Mono<Product> existingProductMono = this.productRepository.findById(id);
        Mono<Product> productRequestMono = Mono.just(product);

        return productRequestMono.zipWith(existingProductMono, (productRequest, existingProduct) ->
                new Product(productRequest.getId(), productRequest.getName(), productRequest.getPrice()))
                .flatMap(this.productRepository::save);
    }

    @Override
    public Mono<Product> save(Product product) {
        return this.productRepository.save(product);
    }

}
