package com.ldubgd.notificationService.services;

import com.ldubgd.components.dao.Statement;
import org.springframework.stereotype.Service;

@Service
public class SendNotificationService {

    public void sendNotificationAboutStatementStatus(Statement statement ) {
        System.out.println(statement.getFullName()+" "+statement.getUser().getEmail());


        new RuntimeException("Not implemented yet");
    }


}
