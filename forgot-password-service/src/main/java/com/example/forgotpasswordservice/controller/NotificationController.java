package com.example.forgotpasswordservice.controller;

import com.example.forgotpasswordservice.security.JwtTokenProvider;
import com.example.forgotpasswordservice.service.NotificationService;
import com.ldubgd.components.dao.Notification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        notificationService.createNotification(userId, message);
        return "Notification created successfully";
    }
}

