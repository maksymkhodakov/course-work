package org.example.producermodule.rabbitmq.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RabbitMqController {

    private final MessageProducerWrapper messageProducerWrapper;

    @PostMapping("/rabbitmq/produce/animal/save")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseEntity<Void> produceCreate(@RequestBody @Valid AnimalDTO animalDTO) {
        messageProducerWrapper.produceDevMessages(animalDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rabbitmq/produce/animal/update")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseEntity<Void> produceUpdate(@RequestBody @Valid AnimalUpdateDTO animalUpdateDTO) {
        messageProducerWrapper.produceDevMessages(animalUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rabbitmq/produce/animal/delete")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseEntity<Void> produceDelete(@RequestBody @Valid AnimalDeleteDTO animalDeleteDTO) {
        messageProducerWrapper.produceDevMessages(animalDeleteDTO);
        return ResponseEntity.ok().build();
    }
}
