package com.example.statementservice.entity;

import com.example.statementservice.entity.enums.StatementStatus;
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
@Table(name = "statement_info")
public class StatementInfo {

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
    @JoinColumn(name = "id")
    private Statement statement;
}
