package com.ldubgd.fileService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ldubgd.components")  // Вказуємо лише пакет модуля з сутностями
@EnableJpaRepositories(basePackages = "com.ldubgd.fileService.dao")  // Пакет для репозиторіїв
public class FileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServiceApplication.class, args);
	}

}
