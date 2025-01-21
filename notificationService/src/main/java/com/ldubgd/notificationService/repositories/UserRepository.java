package com.ldubgd.notificationService.repositories;

import com.ldubgd.components.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
