package com.ldubgd.components.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", length = 36, nullable = false, unique = true)
    private String name;

    @Column(name = "email", length = 128, nullable = false, unique = true)
    private String email;

    @Column(name = "login", length = 128, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "role", length = 128, nullable = false)
    private String role;

    @Column(name = "faculty", length = 128)
    private String faculty;

    @Column(name = "specialty", length = 128)
    private String specialty;

    @Column(name = "degree", length = 128)
    private String degree;

    @Column(name = "student_group", length = 128)
    private String group;

    @Column(name = "phone_number", length = 128)
    private String phoneNumber;

    @Column(name = "date_birth", length = 128)
    private String dateBirth;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Statement> statements = new ArrayList<>();

}
