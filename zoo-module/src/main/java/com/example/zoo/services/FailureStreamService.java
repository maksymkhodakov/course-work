package com.example.zoo.services;

import com.example.zoo.dto.AnimalStreamResultDTO;
import com.example.zoo.entity.AnimalStream;
import com.example.zoo.enums.AnimalStreamProcessType;

import java.util.List;

public interface FailureStreamService {
    void saveUnprocessed(AnimalStreamProcessType processType, AnimalStream animalStream);

    List<AnimalStreamResultDTO> getUnprocessed();

    void delete(Long id);
}
