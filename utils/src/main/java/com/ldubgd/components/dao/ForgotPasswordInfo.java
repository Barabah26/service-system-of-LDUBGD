package com.ldubgd.components.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ldubgd.components.dao.enums.StatementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "forgot_password_info")
public class ForgotPasswordInfo {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "is_ready")
    private Boolean isReady;

    @Enumerated(EnumType.STRING)
    @Column(name = "statement_status")
    private StatementStatus statementStatus;

    @OneToOne
    @MapsId
    @JsonBackReference
    @JoinColumn(name = "id")
    private ForgotPassword forgotPassword;
}
