package com.reactive.pluralsight;

import com.reactive.pluralsight.handlers.ProductHandler;
import com.reactive.pluralsight.model.Product;
import com.reactive.pluralsight.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class PluralsightApplication {

    public static void main(String[] args) {
        SpringApplication.run(PluralsightApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ReactiveMongoOperations reactiveMongoOperations, ProductRepository productRepository) {
        return args -> {

            Flux<Product> productFlux = Flux.just(
                    new Product(null, "Ipad pro", 4000000d),
                    new Product(null, "Imac 19 inch", 5000000d),
                    new Product(null, "Apple tv", 20000000d)
            ).flatMap(productRepository::save);

            productFlux.thenMany(productRepository.findAll())
                    .subscribe(System.out::println);
        };
    }


}
