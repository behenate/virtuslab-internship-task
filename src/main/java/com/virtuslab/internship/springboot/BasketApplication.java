package com.virtuslab.internship.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.virtuslab.internship.springboot","com.virtuslab.internship"} )
@EntityScan("com.virtuslab.internship")

@EnableJpaRepositories("com.virtuslab.internship")
@SpringBootApplication

public class BasketApplication {

    public static void main(String... args) {
        SpringApplication.run(BasketApplication.class, args);
    }
}