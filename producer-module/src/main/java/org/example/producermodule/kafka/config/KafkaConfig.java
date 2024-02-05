package org.example.producermodule.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${kafka.topics.dev}")
    private String topicName;

    @Value("${kafka.topics.animal-stream}")
    private String animalStreamTopic;

    private final LocalValidatorFactoryBean validator;

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(this.validator);
    }

    @Bean
    public NewTopic devTopic() {
        return TopicBuilder.name(topicName)
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic animalStreamTopic() {
        return TopicBuilder.name(animalStreamTopic)
                .partitions(5)
                .build();
    }
}
