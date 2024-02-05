package org.example.consumermodule.service.impl;

import com.example.zoo.entity.AnimalStream;
import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;
import com.example.zoo.repository.AnimalStreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.service.AnimalStreamService;
import org.example.producermodule.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalStreamServiceImpl implements AnimalStreamService {
    private final AnimalStreamRepository animalStreamRepository;
    private final AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @Override
    @Transactional
    public void saveLoad(AnimalStreamDTO animalStreamDTO) {
        final AnimalStreamLoadResult animalStreamLoadResult = animalStreamLoadResultRepository.findById(animalStreamDTO.getLoadId())
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_LOAD_NOT_FOUND));

        final List<AnimalStream> animalStreams = animalStreamDTO.getAnimalStream()
                .stream()
                .map(this::buildAnimalStream)
                .toList();

        animalStreams.forEach(animalStreamLoadResult::addAnimalStream);
        animalStreamLoadResult.setProcessed(true);

        animalStreamLoadResultRepository.save(animalStreamLoadResult);
    }

    @Override
    @Transactional
    public void save(AnimalDTO animalDTO) {
        log.info("Consumer received message value: {}", animalDTO);
        final AnimalStream animalStream = buildAnimalStream(animalDTO);
        final AnimalStream savedAnimal = animalStreamRepository.save(animalStream);
        log.info("Animal with id={} was saved to DB",savedAnimal.getId());
    }

    private AnimalStream buildAnimalStream(AnimalDTO animalDTO) {
        return AnimalStream.builder()
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .venomous(animalDTO.getVenomous())
                .typePowerSupply(animalDTO.getTypePowerSupply())
                .age(animalDTO.getAge())
                .processed(false)
                .build();
    }

    private AnimalStream buildAnimalStream(AnimalStreamFileDTO animalDTO) {
        return AnimalStream.builder()
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .venomous(animalDTO.getVenomous())
                .typePowerSupply(animalDTO.getTypePowerSupply())
                .age(animalDTO.getAge())
                .processed(false)
                .build();
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
