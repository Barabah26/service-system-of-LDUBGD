package com.ldubgd.notificationService.services;

import com.ldubgd.components.dao.Statement;
import com.ldubgd.utils.CryptoTool;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class SendNotificationService {

    private final WebClient.Builder webClientBuilder;

    @Value("${emailService.url}")
    private String emailServiceUrl;

    public SendNotificationService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Autowired
    private  CryptoTool cryptoTool;

    public void sendNotificationAboutStatementStatus(Long statementId) {
        try {
            String hashedId = cryptoTool.hashOf(statementId);
            URI uri = UriComponentsBuilder
                    .fromUriString(emailServiceUrl)
                    .path("/email/send")
                    .queryParam("id", hashedId)
                    .build()
                    .toUri();

            System.out.println("Sending notification to: " + uri);

            webClientBuilder.build()
                    .patch()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .toBodilessEntity()
                    .doOnSuccess(response -> System.out.println("Notification sent successfully"))
                    .doOnError(error -> System.err.println("Error while sending notification: " + error.getMessage()))
                    .block();
        } catch (Exception e) {
            System.err.println("Exception occurred while sending notification: " + e.getMessage());
        }
    }
}

