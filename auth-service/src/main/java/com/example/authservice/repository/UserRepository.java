package com.example.authservice.repository;

import com.ldubgd.components.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUsersByLogin(String login);

    Optional<User> findByRole(String role);

}
