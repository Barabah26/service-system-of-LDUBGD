package com.ldubgd.notificationService.configs;

import com.ldubgd.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class NotificationServiceConfig {
    @Value("${salt}")
    private String salt;

    @Bean(destroyMethod="shutdown")
    public ScheduledExecutorService taskScheduler() {
        return Executors.newScheduledThreadPool(1); // Вказуйєте кількість потоків, яка вам потрібна
    }

    @Bean
    public CryptoTool getCryptoTool(){
        return new CryptoTool(salt);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }


    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Дозволяє будь-яку IP-адресу
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
