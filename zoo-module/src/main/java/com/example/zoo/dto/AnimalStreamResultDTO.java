package com.example.zoo.dto;

import java.sql.Timestamp;

import com.example.zoo.enums.AnimalStreamProcessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalStreamResultDTO {
    private Long id;
    private String name;
    private String kindAnimal;
    private String venomous;
    private String typePowerSupply;
    private String age;
    private Timestamp uploadedDate;
    private String filename;
    private String errorMessage;
    private AnimalStreamProcessType processType;
}
