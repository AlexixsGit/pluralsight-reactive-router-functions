package com.reactive.pluralsight.controller;

import com.reactive.pluralsight.model.Product;
import com.reactive.pluralsight.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductControllerTest {

    private WebTestClient webTestClient;
    private List<Product> expectedProducts;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void beforeEach() {
        this.webTestClient = WebTestClient.bindToController(new ProductController(productService))
                .configureClient()
                .baseUrl("/productsController")
                .build();

        this.expectedProducts = this.productService.findAll().collectList().block();
    }

    @Test
    public void getAllProductsTest() {
        FluxExchangeResult<Product> fluxExchangeResult = this.webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Product.class);

        StepVerifier.create(fluxExchangeResult.getResponseBody())
                .expectNextCount(3).verifyComplete();
    }
}
