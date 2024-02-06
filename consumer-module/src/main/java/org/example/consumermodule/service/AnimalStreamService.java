package org.example.consumermodule.service;

import com.example.zoo.entity.AnimalStream;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;

import java.util.List;

public interface AnimalStreamService {
    void processAnimalStreams(List<AnimalStream> animalStreams);
    void saveLoad(AnimalStreamDTO animalStreamDTO);
    void save(AnimalDTO animalDTO);
    void update(AnimalUpdateDTO animalUpdateDTO);
    void delete(AnimalDeleteDTO animalDeleteDTO);
}
