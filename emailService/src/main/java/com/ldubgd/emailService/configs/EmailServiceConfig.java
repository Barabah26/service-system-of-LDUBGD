package com.ldubgd.emailService.configs;

import com.ldubgd.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailServiceConfig {
    @Value("${salt}")
    private String salt;


    @Bean
    public CryptoTool getCryptoTool(){
        return new CryptoTool(salt);
    }

}
