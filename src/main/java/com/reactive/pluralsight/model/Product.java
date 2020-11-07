package com.reactive.pluralsight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private Double price;
}
