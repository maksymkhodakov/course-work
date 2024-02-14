package org.example.producermodule.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("topics")
public class TopicNameConfig {
    private String devSave;
    private String devUpdate;
    private String devDelete;
    private String animalStream;
}
