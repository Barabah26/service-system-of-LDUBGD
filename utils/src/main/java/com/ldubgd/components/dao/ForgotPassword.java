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
@Table(name = "forgotPassword")
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_of_forgot_password", nullable = false)
    private String typeOfForgotPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToOne(mappedBy = "forgotPassword", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ForgotPasswordInfo forgotPasswordInfo;
}
