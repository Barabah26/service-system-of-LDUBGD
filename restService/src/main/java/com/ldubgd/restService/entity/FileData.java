package com.ldubgd.restService.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "file_data")
@Data
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info_id", unique = true)
    private FileInfo fileInfo;
}