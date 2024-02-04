package org.example.consumermodule.service.impl;

import com.example.zoo.entity.AnimalStream;
import com.example.zoo.repository.AnimalStreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalStreamServiceImpl implements AnimalStreamService {
    private final AnimalStreamRepository animalStreamRepository;

    @Override
    @Transactional
    public void save(AnimalDTO animalDTO) {
        log.info("Consumer received message value: {}", animalDTO);
        final AnimalStream animalStream = AnimalStream.builder()
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .venomous(animalDTO.getVenomous())
                .typePowerSupply(animalDTO.getTypePowerSupply())
                .age(animalDTO.getAge())
                .build();

        final AnimalStream savedAnimal = animalStreamRepository.save(animalStream);
        log.info("Animal with id={} was saved to DB",savedAnimal.getId());
    }

    @Override
    @Transactional
    public void update(AnimalUpdateDTO animalUpdateDTO) {
        final AnimalStream animalStream = animalStreamRepository.findById(animalUpdateDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal in DB not found"));

        animalStream.setName(animalUpdateDTO.getName());
        animalStream.setAge(animalUpdateDTO.getAge());
        animalStream.setVenomous(animalUpdateDTO.getVenomous());
        animalStream.setTypePowerSupply(animalUpdateDTO.getTypePowerSupply());
        animalStream.setAge(animalUpdateDTO.getAge());

        animalStreamRepository.save(animalStream);
        log.info("Animal updated with id={}", animalStream.getId());
    }

    @Override
    @Transactional
    public void delete(AnimalDeleteDTO animalDeleteDTO) {
        final AnimalStream animalStream = animalStreamRepository.findById(animalDeleteDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal in kafka not found"));
        animalStreamRepository.delete(animalStream);
        log.info("Animal in kafka deleted with id={}", animalDeleteDTO.getId());
    }
}
