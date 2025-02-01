package com.example.forgotpasswordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ldubgd.components.dao")
public class ForgotPasswordServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForgotPasswordServiceApplication.class, args);
        System.out.println("http://localhost:8095/swagger-ui/index.html");
    }

}
