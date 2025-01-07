package com.example.userprofileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserprofileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserprofileServiceApplication.class, args);
        System.out.println("http://localhost:8090/swagger-ui/index.html");

    }

}
