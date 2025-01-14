package com.example.statementservice.entity;

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

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "year_birthday")
    private String yearBirthday;

    @Column(name = "group_name")
    private String group;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "type_of_statement")
    private String typeOfStatement;

    @JsonManagedReference
    @OneToOne(mappedBy = "statement", cascade = CascadeType.ALL, orphanRemoval = true)
    private StatementInfo statementInfo;
}

