package com.reactive.pluralsight.handlers;

import com.reactive.pluralsight.model.Product;
import com.reactive.pluralsight.model.ProductEvent;
import com.reactive.pluralsight.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductRepository productRepository;

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        Flux<Product> productFlux = this.productRepository.findAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productFlux, Product.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Product> productMono = this.productRepository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono.flatMap(product ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(product)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
        Mono<Product> productMono = serverRequest.bodyToMono(Product.class);

        return productMono.flatMap(product -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON).body(this.productRepository.save(product), Product.class));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        System.out.println("productId: "+id);
        Mono<Product> existingProductMono = this.productRepository.findById(id);
        Mono<Product> productMono = serverRequest.bodyToMono(Product.class);

        Mono<ServerResponse> noFound = ServerResponse.notFound().build();

        return productMono.zipWith(existingProductMono, (product, existingProduct) ->
                new Product(product.getId(), product.getName(), product.getPrice()))
                .flatMap(product -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(this.productRepository.save(product), Product.class))
                .switchIfEmpty(noFound);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Product> productMono = this.productRepository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return productMono.flatMap(existingProduct -> ServerResponse.ok().
                build(this.productRepository.delete(existingProduct)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteAllProducts(ServerRequest serverRequest) {
        return ServerResponse.ok().build(this.productRepository.deleteAll());
    }

    public Mono<ServerResponse> getProductEvents(ServerRequest serverRequest) {
        Flux<ProductEvent> eventFlux = Flux.interval(Duration.ofSeconds(1)).map(val ->
                new ProductEvent(val, "Product Event"));

        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventFlux, ProductEvent.class);
    }
}

