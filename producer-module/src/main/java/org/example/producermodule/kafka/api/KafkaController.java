package org.example.producermodule.kafka.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.example.producermodule.kafka.config.TopicNameConfig;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KafkaController {
    private final TopicNameConfig topicNameConfig;
    private final KafkaSenderService kafkaSenderService;

    @PostMapping("/kafka/produce/animal/save")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseEntity<Void> produceCreate(@RequestBody @Valid AnimalDTO animalDTO) {
        kafkaSenderService.produceMessages(topicNameConfig.getDevSave(), animalDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/kafka/produce/animal/update")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseEntity<Void> produceUpdate(@RequestBody @Valid AnimalUpdateDTO animalUpdateDTO) {
        kafkaSenderService.produceMessages(topicNameConfig.getDevUpdate(), animalUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/kafka/produce/animal/delete")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseEntity<Void> produceDelete(@RequestBody @Valid AnimalDeleteDTO animalDeleteDTO) {
        kafkaSenderService.produceMessages(topicNameConfig.getDevDelete(), animalDeleteDTO);
        return ResponseEntity.ok().build();
    }
}
