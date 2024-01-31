package org.example.consumermodule.service;


import org.example.producermodule.domain.dto.AnimalDTO;
import org.example.producermodule.domain.dto.AnimalDeleteDTO;
import org.example.producermodule.domain.dto.AnimalUpdateDTO;

public interface AnimalStreamService {
    void save(AnimalDTO animalDTO);
    void update(AnimalUpdateDTO animalUpdateDTO);
    void delete(AnimalDeleteDTO animalDeleteDTO);
}
