package com.example.zoo.services;

import com.example.zoo.dto.AnimalStreamResultDTO;

import java.util.List;

public interface FailureStreamService {
    List<AnimalStreamResultDTO> getUnprocessed();

    void delete(Long id);
}
