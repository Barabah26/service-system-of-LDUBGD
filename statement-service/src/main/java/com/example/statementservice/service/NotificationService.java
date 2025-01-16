package com.example.statementservice.service;

import com.ldubgd.components.dao.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(Long userId, String message);

    List<Notification> getNotifications(Long userId);

    void markAsRead(Long notificationId);

}