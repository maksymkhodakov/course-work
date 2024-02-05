package com.example.zoo.repository;

import com.example.zoo.entity.AnimalStreamLoadResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalStreamLoadResultRepository extends JpaRepository<AnimalStreamLoadResult, Long> {
    List<AnimalStreamLoadResult> getAnimalStreamLoadResultByProcessedIsFalse();
}
