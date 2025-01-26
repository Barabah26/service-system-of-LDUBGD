package com.ldubgd.notificationService.services;

import com.ldubgd.components.dao.Statement;
import com.ldubgd.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SendNotificationService {

    private final WebClient.Builder webClientBuilder;


    public SendNotificationService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Autowired
    private  CryptoTool cryptoTool;

    public void sendNotificationAboutStatementStatus(Long statementId) {
        String url = String.format(
                "http://localhost:8060/api/email/send?id=%s",
                cryptoTool.hashOf(statementId)
        );

        try {
            webClientBuilder.build()
                    .patch()
                    .uri(url)
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

