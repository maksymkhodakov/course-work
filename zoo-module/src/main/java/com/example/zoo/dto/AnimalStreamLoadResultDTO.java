package com.example.zoo.dto;

import com.example.zoo.enums.AnimalStreamProcessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalStreamLoadResultDTO {
    private Long id;
    private AnimalStreamProcessType processType;
    private String filename;
    private String s3Link;
    private Timestamp createDate;
    private boolean isProcessed;
}
