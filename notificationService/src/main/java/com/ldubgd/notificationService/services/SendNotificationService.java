package com.ldubgd.notificationService.services;

import com.ldubgd.components.dao.Statement;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SendNotificationService {

    private final WebClient.Builder webClientBuilder;

    public SendNotificationService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void sendNotificationAboutStatementStatus(Statement statement ) {

    }


}
