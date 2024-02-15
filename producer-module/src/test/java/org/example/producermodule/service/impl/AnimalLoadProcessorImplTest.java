package org.example.producermodule.service.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.storage.config.AWSProperties;
import com.example.zoo.storage.service.S3Service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.example.producermodule.dto.AnimalStreamFileDTO;
import org.example.producermodule.kafka.config.TopicNameConfig;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.example.producermodule.service.FileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AnimalLoadProcessorImpl.class, TopicNameConfig.class, AWSProperties.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimalLoadProcessorImplTest {
    @Autowired
    private AnimalLoadProcessorImpl animalLoadProcessorImpl;

    @MockBean
    private AWSProperties aWSProperties;

    @MockBean
    private FileReader fileReader;

    @MockBean
    private KafkaSenderService kafkaSenderService;

    @MockBean
    private MessageProducerWrapper messageProducerWrapper;

    @MockBean
    private S3Service s3Service;

    @MockBean
    private TopicNameConfig topicNameConfig;

    /**
     * Method under test:
     * {@link AnimalLoadProcessorImpl#process(AnimalStreamLoadResult)}
     */
    @Test
    void testProcess() throws UnsupportedEncodingException {
        // Arrange
        when(s3Service.downloadFile(any(), any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        when(fileReader.parseData(any(), any(), Mockito.<Class<AnimalStreamFileDTO>>any()))
                .thenReturn(new ArrayList<>());
        doNothing().when(kafkaSenderService).produceMessages(any(), any());

        AnimalStreamLoadResult load = new AnimalStreamLoadResult();
        load.setAnimalStreams(new ArrayList<>());
        load.setCreateDate(mock(Timestamp.class));
        load.setFilename("foo.txt");
        load.setId(1L);
        load.setLastUpdateDate(mock(Timestamp.class));
        load.setProcessType(AnimalStreamProcessType.KAFKA);
        load.setProcessed(true);
        load.setS3Link("S3 Link");
        load.setToDelete(true);

        // Act
        animalLoadProcessorImpl.process(load);

        // Assert
        verify(aWSProperties, times(1)).getZooServiceAnimalStreamLoadBucketName();
        verify(s3Service).downloadFile(isNull(), eq("S3 Link"));
        verify(topicNameConfig, times(1)).getAnimalStream();
        verify(kafkaSenderService).produceMessages(isNull(), any());
        verify(fileReader).parseData(any(), eq("foo.txt"), any());
    }

    /**
     * Method under test:
     * {@link AnimalLoadProcessorImpl#process(AnimalStreamLoadResult)}
     */
    @Test
    void testProcess2() throws UnsupportedEncodingException {
        // Arrange
        when(s3Service.downloadFile(any(), any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        when(fileReader.parseData(any(), any(), any()))
                .thenReturn(new ArrayList<>());
        doNothing().when(messageProducerWrapper).produceAnimalStreamMessages(any());

        AnimalStreamLoadResult load = new AnimalStreamLoadResult();
        load.setAnimalStreams(new ArrayList<>());
        load.setCreateDate(mock(Timestamp.class));
        load.setFilename("foo.txt");
        load.setId(1L);
        load.setLastUpdateDate(mock(Timestamp.class));
        load.setProcessType(AnimalStreamProcessType.RABBIT_MQ);
        load.setProcessed(true);
        load.setS3Link("S3 Link");
        load.setToDelete(true);

        // Act
        animalLoadProcessorImpl.process(load);

        // Assert
        verify(aWSProperties, times(1)).getZooServiceAnimalStreamLoadBucketName();
        verify(s3Service, times(1)).downloadFile(isNull(), eq("S3 Link"));
        verify(messageProducerWrapper, times(1)).produceAnimalStreamMessages(any());
        verify(fileReader, times(1)).parseData(any(), eq("foo.txt"), any());
    }
}
