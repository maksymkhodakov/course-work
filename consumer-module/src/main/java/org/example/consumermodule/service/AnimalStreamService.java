package org.example.consumermodule.service;

import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;

public interface AnimalStreamService {
    void saveLoad(AnimalStreamDTO animalStreamDTO);
    void save(AnimalDTO animalDTO);
    void update(AnimalUpdateDTO animalUpdateDTO);
    void delete(AnimalDeleteDTO animalDeleteDTO);
}
