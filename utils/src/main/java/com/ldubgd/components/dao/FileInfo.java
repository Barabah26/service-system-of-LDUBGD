package com.ldubgd.components.dao;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Column(name = "statement_id",unique = true)
    private Long statementId;


    @OneToOne(mappedBy = "fileInfo", cascade = CascadeType.ALL)
    private FileData fileData;


}