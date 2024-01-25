package org.example.consumermodule.listeners;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.domain.entity.Animal;
import org.example.consumermodule.repositories.AnimalRepository;
import org.example.producermodule.global.GlobalConstants;
import org.example.producermodule.rabbitmq.domain.dto.AnimalDTO;
import org.example.producermodule.rabbitmq.domain.dto.AnimalUpdateDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = {GlobalConstants.DEV_QUEUE_NAME})
public class AnimalCreationListener {
    private final AnimalRepository animalRepository;

    @RabbitHandler
    @Transactional
    public void save(AnimalDTO animalDTO) {
        final Animal animal = Animal.builder()
                    .name(animalDTO.getName())
                    .age(animalDTO.getAge())
                    .venomous(animalDTO.isVenomous())
                    .build();
        final Animal savedAnimal = animalRepository.save(animal);
        log.info("Animal saved with id={}", savedAnimal.getId());
    }

    @RabbitHandler
    @Transactional
    public void update(AnimalUpdateDTO animalUpdateDTO) {
        final Animal animal = animalRepository.findById(animalUpdateDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal not found"));

        animal.setName(animalUpdateDTO.getName());
        animal.setAge(animalUpdateDTO.getAge());
        animal.setVenomous(animalUpdateDTO.isVenomous());

        animalRepository.save(animal);
        log.info("Animal updated with id={}", animal.getId());
    }

    @RabbitHandler
    @Transactional
    public void delete(Long id) {
        final Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        animalRepository.delete(animal);
        log.info("Animal deleted with id={}", id);
    }

}
