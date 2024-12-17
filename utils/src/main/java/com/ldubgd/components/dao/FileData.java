package com.ldubgd.components.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "file_data")
@Data
@Getter
@Setter
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info_id", unique = true)
    private FileInfo fileInfo;
}