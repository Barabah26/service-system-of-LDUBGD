package com.ldubgd.components.dao;

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

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "group_name", nullable = false)
    private String group;

    @Column(name = "faculty", nullable = false)
    private String faculty;

    @Column(name = "type_of_forgot_password", nullable = false)
    private String typeOfForgotPassword;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
