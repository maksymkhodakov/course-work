package com.example.zoo.repository;

import com.example.zoo.entity.AnimalStream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalStreamRepository extends JpaRepository<AnimalStream, Long> {
    List<AnimalStream> findByProcessedIsNull();
    List<AnimalStream> findByProcessedIsFalse();
}
