package org.example.producermodule.service.impl;

import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.enums.KindAnimal;
import com.example.zoo.enums.TypePowerSupply;
import lombok.RequiredArgsConstructor;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.example.producermodule.enums.EventType;
import org.example.producermodule.kafka.config.TopicNameConfig;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.example.producermodule.service.ProducerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final TopicNameConfig topicNameConfig;
    private final KafkaSenderService kafkaSenderService;
    private final MessageProducerWrapper messageProducerWrapper;

    @Override
    public void handleDelete(AnimalStreamProcessType processType, Integer id) {
        final AnimalDeleteDTO animalDeleteDTO = AnimalDeleteDTO.builder()
                .id(Long.valueOf(id))
                .build();
        if (processType == AnimalStreamProcessType.KAFKA) {
            kafkaSenderService.produceMessages(topicNameConfig.getDevSave(), animalDeleteDTO);
        } else {
            messageProducerWrapper.produceDevMessages(animalDeleteDTO);
        }
    }

    @Override
    public void handleUpdate(AnimalStreamProcessType processType, Integer id, Integer age, String name, KindAnimal kindAnimal, boolean venomous, TypePowerSupply typePowerSupply) {
        final AnimalUpdateDTO animalUpdateDTO =AnimalUpdateDTO.builder()
                .id(Long.valueOf(id))
                .name(name)
                .age(String.valueOf(age))
                .kindAnimal(String.valueOf(kindAnimal))
                .venomous(String.valueOf(venomous))
                .typePowerSupply(String.valueOf(typePowerSupply))
                .build();
        if (processType == AnimalStreamProcessType.KAFKA) {
            kafkaSenderService.produceMessages(topicNameConfig.getDevSave(), animalUpdateDTO);
        } else {
            messageProducerWrapper.produceDevMessages(animalUpdateDTO);
        }
    }

    @Override
    public void handleSave(AnimalStreamProcessType processType, Integer age, String name, KindAnimal kindAnimal, boolean venomous, TypePowerSupply typePowerSupply) {
        final AnimalDTO animalDTO = AnimalDTO.builder()
                .name(name)
                .age(String.valueOf(age))
                .kindAnimal(String.valueOf(kindAnimal))
                .venomous(String.valueOf(venomous))
                .typePowerSupply(String.valueOf(typePowerSupply))
                .build();
        if (processType == AnimalStreamProcessType.KAFKA) {
            kafkaSenderService.produceMessages(topicNameConfig.getDevSave(), animalDTO);
        } else {
            messageProducerWrapper.produceDevMessages(animalDTO);
        }
    }
}
