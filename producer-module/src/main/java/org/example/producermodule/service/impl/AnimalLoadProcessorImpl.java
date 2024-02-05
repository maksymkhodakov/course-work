package org.example.producermodule.service.impl;

import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.storage.config.AWSProperties;
import com.example.zoo.storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producermodule.dto.AnimalStreamDTO;
import org.example.producermodule.dto.AnimalStreamFileDTO;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.example.producermodule.service.AnimalLoadProcessor;
import org.example.producermodule.service.FileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalLoadProcessorImpl implements AnimalLoadProcessor {
    @Value("${kafka.topics.animal-stream}")
    private String topic;

    private final S3Service s3Service;
    private final AWSProperties awsProperties;
    private final FileReader fileReader;
    private final KafkaSenderService kafkaSenderService;
    private final MessageProducerWrapper messageProducerWrapper;

    @Override
    public void process(AnimalStreamLoadResult load) {
        final String filename = load.getFilename();
        final String s3Link = load.getS3Link();
        final AnimalStreamProcessType type = load.getProcessType();
        final byte[] fileBytes = s3Service.downloadFile(awsProperties.getZooServiceAnimalStreamLoadBucketName(), s3Link);
        final List<AnimalStreamFileDTO> animalStream = fileReader.parseData(fileBytes, filename, AnimalStreamFileDTO.class);
        final AnimalStreamDTO animalStreamDTO = buildAnimalStreamDTO(load, animalStream);
        sendToBroker(type, animalStreamDTO);
    }

    private AnimalStreamDTO buildAnimalStreamDTO(AnimalStreamLoadResult load, List<AnimalStreamFileDTO> animalStream) {
        return AnimalStreamDTO.builder()
                .loadId(load.getId())
                .animalStream(animalStream)
                .build();
    }

    private void sendToBroker(AnimalStreamProcessType type, AnimalStreamDTO animalStreamDTO) {
        if (type == AnimalStreamProcessType.RABBIT_MQ) {
            messageProducerWrapper.produceAnimalStreamMessages(animalStreamDTO);
            log.info("Producer sent animal load to RabbitMQ");
        } else {
            kafkaSenderService.produceMessages(topic, animalStreamDTO);
            log.info("Producer sent animal load to Kafka");
        }
    }
}
