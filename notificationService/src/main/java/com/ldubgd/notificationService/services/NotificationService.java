package com.ldubgd.notificationService.services;


import com.ldubgd.components.dao.ForgotPasswordInfo;
import com.ldubgd.components.dao.Notification;
import com.ldubgd.components.dao.StatementInfo;
import com.ldubgd.components.dao.User;
import com.ldubgd.components.dao.enums.StatementStatus;
import com.ldubgd.notificationService.repositories.ForgotPasswordInfoRepository;
import com.ldubgd.notificationService.repositories.NotificationRepository;
import com.ldubgd.notificationService.repositories.StatementInfoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
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

        List<ForgotPasswordInfo> forgotPasswordInfos = forgotPasswordInfoRepository
                .findForgotPasswordInfosByIsReadyAndStatementStatus(false, StatementStatus.READY);

        statements.forEach(statement -> {
            sendNotificationService.sendNotificationAboutStatementStatus(statement.getId());

            if (statement.getStatementStatus() == StatementStatus.READY) {
                User user = statement.getStatement().getUser();
                String message = "Ваша заявка готова! Перевірте електронну пошту або зверніться в деканат!";

                boolean alreadyExists = notificationRepository.existsByUserIdAndMessage(user.getUserId(), message);

                if (!alreadyExists) {
                    createNotification(user.getUserId(), message);
                }
            }
        });

        forgotPasswordInfos.forEach(forgotPassword -> {

            if (forgotPassword.getStatementStatus() == StatementStatus.READY) {
                User user = forgotPassword.getForgotPassword().getUser();
                String message = "Ваша заявка готова! Перевірте електронну пошту або зверніться в деканат!";

                boolean alreadyExists = notificationRepository.existsByUserIdAndMessage(user.getUserId(), message);

                if (!alreadyExists) {
                    createNotification(user.getUserId(), message);
                }
            }
        });
    }


    public void createNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);
    }

}
