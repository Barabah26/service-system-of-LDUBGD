package com.ldubgd.notificationService.controller;

import com.ldubgd.components.dao.Notification;
import com.ldubgd.components.dao.User;
import com.ldubgd.notificationService.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotifications(@RequestParam Long userId) {
        return notificationService.getNotifications(userId);
    }

    @PostMapping("/{notificationId}/read")
    public void markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
    }

    @PostMapping("/ready")
    public String messageToUser(@RequestParam Long userId, @RequestParam String message) {
        notificationService.createNotification(userId, message, null, null);
        return "Notification created successfully";
    }

}

