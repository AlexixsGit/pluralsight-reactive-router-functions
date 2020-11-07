package com.reactive.pluralsight.config;

import com.reactive.pluralsight.handlers.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
        return route(GET("/products").and(accept(MediaType.APPLICATION_JSON)), productHandler::getAllProducts)
                .andRoute(GET("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productHandler::getProduct)
                .andRoute(POST("/products").and(accept(MediaType.APPLICATION_JSON)), productHandler::saveProduct)
                .andRoute(PUT("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productHandler::updateProduct)
                .andRoute(DELETE("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productHandler::deleteProduct)
                .andRoute(DELETE("/products").and(accept(MediaType.APPLICATION_JSON)), productHandler::deleteAllProducts);
    }
}
