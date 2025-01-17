package com.ldubgd.notificationService.services;


import com.ldubgd.notificationService.repositories.StatementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private ScheduledExecutorService scheduler;

    private Runnable task;

    @PostConstruct
    public void init() {
        task = () -> {
            try {
                updateStatementStatus();
            } catch (Exception e) {
                System.out.println("Помилка відправки повідомлення про статус заявки: " + e.getMessage());
            }
        };
        scheduler.scheduleAtFixedRate(
                task,
                0,1,
                TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdown();
        System.out.println("Зупинено відправку повідомлень про статус заявки");
    }

    private void updateStatementStatus () {

    }

}
