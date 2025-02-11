package com.ldubgd.notificationService.services;


import com.ldubgd.components.dao.*;
import com.ldubgd.components.dao.enums.StatementStatus;
import com.ldubgd.notificationService.repositories.ForgotPasswordInfoRepository;
import com.ldubgd.notificationService.repositories.NotificationRepository;
import com.ldubgd.notificationService.repositories.StatementInfoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private StatementInfoRepository statementInfoRepository;

    @Autowired
    private SendNotificationService sendNotificationService;

    @Autowired
    private ForgotPasswordInfoRepository forgotPasswordInfoRepository;

    @Autowired
    private ScheduledExecutorService scheduler;

    @Autowired
    private NotificationRepository notificationRepository;

    private Runnable task;

    @PostConstruct
    public void init() {
        task = () -> {
            try {
                updateStatementStatus();
                updateForgotPasswordStatus();
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

    private void updateStatementStatus() {
        List<StatementInfo> statements = statementInfoRepository
                .findStatementInfosByIsReadyAndStatementStatus(false, StatementStatus.READY);

        statements.forEach(statement -> {
            sendNotificationService.sendNotificationAboutStatementStatus(statement.getId());

            if (statement.getStatementStatus() == StatementStatus.READY) {
                User user = statement.getStatement().getUser();
                String message = "Ваша довідка готова! Перевірте електронну пошту або зверніться в деканат!";

                boolean alreadyExists = notificationRepository.existsByUserIdAndMessage(user.getUserId(), message);

                if (!alreadyExists) {
                    createNotification(user.getUserId(), message, statement, null);
                }
            }
        });

    }

    @Transactional
    public void updateForgotPasswordStatus() {
        List<ForgotPasswordInfo> forgotPasswordInfos = forgotPasswordInfoRepository
                .findForgotPasswordInfosByIsReadyAndStatementStatus(false, StatementStatus.READY);

        forgotPasswordInfos.forEach(forgotPasswordInfo -> {
            ForgotPassword forgotPasswordEntity = forgotPasswordInfo.getForgotPassword();
            User user = forgotPasswordEntity.getUser();

            String message = "Ваша заявка на відновлення пароля готова! Перевірте електронну пошту або зверніться в деканат!";

            // Перевіряємо, чи існує сповіщення для цього користувача та цієї конкретної заявки
            boolean alreadyExists = notificationRepository.existsByUserIdAndForgotPasswordInfoId(user.getUserId(), forgotPasswordInfo.getId());

            // Якщо сповіщення ще не існує, створюємо його
            if (!alreadyExists) {
                // Відправляємо сповіщення про готовність заявки на відновлення пароля
                sendNotificationService.sendNotificationAboutForgotPassword(forgotPasswordEntity.getId());
                createNotification(user.getUserId(), message, null, forgotPasswordInfo);
            }
        });
    }

    public void createNotification(Long userId, String message, StatementInfo statementInfo, ForgotPasswordInfo forgotPasswordInfo) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);

        if (statementInfo != null) {
            notification.setStatementInfo(statementInfo);
        }

        if (forgotPasswordInfo != null) {
            notification.setForgotPasswordInfo(forgotPasswordInfo);
        }

        notificationRepository.save(notification);
    }


    @Transactional
    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);
    }

}
