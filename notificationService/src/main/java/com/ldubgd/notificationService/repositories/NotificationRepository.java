package com.ldubgd.notificationService.repositories;

import com.ldubgd.components.dao.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndIsReadFalse(Long userId);
    boolean existsByUserIdAndMessage(Long userId, String message);

}

