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
@Table(name = "statement")
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "year_birthday", nullable = false)
    private String yearBirthday;

    @Column(name = "group_name", nullable = false)
    private String group;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "faculty", nullable = false)
    private String faculty;

    @Column(name = "type_of_statement", nullable = false)
    private String typeOfStatement;

    @JsonManagedReference
    @OneToOne(mappedBy = "statement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private StatementInfo statementInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
