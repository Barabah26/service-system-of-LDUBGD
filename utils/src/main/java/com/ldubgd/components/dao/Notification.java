package com.ldubgd.components.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statement_info_id", nullable = true)
    private StatementInfo statementInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forgot_password_info_id", nullable = true)
    @JsonManagedReference
    private ForgotPasswordInfo forgotPasswordInfo;

}
