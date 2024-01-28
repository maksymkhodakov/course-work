package org.example.consumermodule.rabbitmq.listeners;

import org.example.consumermodule.domain.entity.AnimalStream;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.domain.repositories.AnimalRepository;
import org.example.producermodule.rabbitmq.global.GlobalConstants;
import org.example.producermodule.domain.dto.AnimalDTO;
import org.example.producermodule.domain.dto.AnimalUpdateDTO;
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
        final AnimalStream animalStream = AnimalStream.builder()
                    .name(animalDTO.getName())
                    .age(animalDTO.getAge())
                    .venomous(animalDTO.isVenomous())
                    .build();
        final AnimalStream savedAnimalStream = animalRepository.save(animalStream);
        log.info("Animal saved with id={}", savedAnimalStream.getId());
    }

    @RabbitHandler
    @Transactional
    public void update(AnimalUpdateDTO animalUpdateDTO) {
        final AnimalStream animalStream = animalRepository.findById(animalUpdateDTO.getId())
                .orElseThrow(() -> new RuntimeException("Animal not found"));

        animalStream.setName(animalUpdateDTO.getName());
        animalStream.setAge(animalUpdateDTO.getAge());
        animalStream.setVenomous(animalUpdateDTO.isVenomous());

        animalRepository.save(animalStream);
        log.info("Animal updated with id={}", animalStream.getId());
    }

    @RabbitHandler
    @Transactional
    public void delete(Long id) {
        final AnimalStream animalStream = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        animalRepository.delete(animalStream);
        log.info("Animal deleted with id={}", id);
    }

}
