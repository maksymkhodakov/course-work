package com.example.zoo.repository;

import com.example.zoo.entity.AnimalStream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalStreamRepository extends JpaRepository<AnimalStream, Long> {
}
