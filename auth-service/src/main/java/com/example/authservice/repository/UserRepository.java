package com.example.authservice.repository;

import com.ldubgd.components.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUsersByLogin(String login);

    @Modifying
    @Transactional
    @Query("UPDATE User a SET a.password = :newPassword WHERE a.login = :login")
    void updateUserByLogin(@Param("login") String login,
                              @Param("newPassword") String newPassword);

}
