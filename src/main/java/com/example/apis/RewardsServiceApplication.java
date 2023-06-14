package com.example.apis;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class RewardsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardsServiceApplication.class, args);
    }

}
