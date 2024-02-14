package org.example.producermodule.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig implements KafkaListenerConfigurer {
    private final TopicNameConfig topicNameConfig;
    private final LocalValidatorFactoryBean validator;

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(this.validator);
    }

    @Bean
    public NewTopic devSaveTopic() {
        return TopicBuilder.name(topicNameConfig.getDevSave())
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic devUpdateTopic() {
        return TopicBuilder.name(topicNameConfig.getDevUpdate())
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic devDeleteTopic() {
        return TopicBuilder.name(topicNameConfig.getDevDelete())
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic animalStreamTopic() {
        return TopicBuilder.name(topicNameConfig.getAnimalStream())
                .partitions(5)
                .build();
    }
}
