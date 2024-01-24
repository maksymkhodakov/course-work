package org.example.consumermodule.listeners;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.consumermodule.domain.entity.Animal;
import org.example.consumermodule.repositories.AnimalRepository;
import org.example.producermodule.rabbitmq.domain.dto.CreateAnimalDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalCreationListener {
    private final AnimalRepository animalRepository;

    @RabbitListener(queues = {"${rabbit.handler.routing_key}"})
    public void listenDev(CreateAnimalDTO animalDTO) {
        log.info(animalDTO.toString());
        final Animal animal = Animal.builder()
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .venomous(animalDTO.isVenomous())
                .build();
        animalRepository.save(animal);
    }

}
