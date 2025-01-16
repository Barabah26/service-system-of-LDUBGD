package com.example.statementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ldubgd.components")
public class StatementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatementServiceApplication.class, args);
        System.out.println("http://localhost:9080/swagger-ui/index.html");

    }

}
