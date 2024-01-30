package org.example.producermodule.kafka.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.domain.dto.AnimalDTO;
import org.example.producermodule.domain.dto.AnimalDeleteDTO;
import org.example.producermodule.domain.dto.AnimalUpdateDTO;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KafkaController {
    private final KafkaSenderService kafkaSenderService;

    @PostMapping("/kafka/produce/animal/save")
    public ResponseEntity<Void> produceCreate(@RequestBody @Valid AnimalDTO animalDTO) {
        kafkaSenderService.produceMessages(animalDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/kafka/produce/animal/update")
    public ResponseEntity<Void> produceUpdate(@RequestBody @Valid AnimalUpdateDTO animalUpdateDTO) {
        kafkaSenderService.produceMessages(animalUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/kafka/produce/animal/delete")
    public ResponseEntity<Void> produceDelete(@RequestBody @Valid AnimalDeleteDTO animalDeleteDTO) {
        kafkaSenderService.produceMessages(animalDeleteDTO);
        return ResponseEntity.ok().build();
    }
}
