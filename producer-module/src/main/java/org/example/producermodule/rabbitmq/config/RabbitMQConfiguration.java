package org.example.producermodule.rabbitmq.config;

import org.example.producermodule.rabbitmq.global.GlobalConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue devQueue() {
        return QueueBuilder.durable(GlobalConstants.DEV_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", GlobalConstants.DLX_NAME)
                .build();
    }

    @Bean
    public DirectExchange devExchange() {
        return new DirectExchange(GlobalConstants.DEV_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingMessages() {
        return BindingBuilder.bind(devQueue()).to(devExchange()).with(GlobalConstants.DEV_ROUTING_KEY);
    }

    @Bean
    public Queue animalStreamQueue() {
        return QueueBuilder.durable(GlobalConstants.ANIMAl_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", GlobalConstants.DLX_NAME)
                .build();
    }

    @Bean
    public DirectExchange animalStreamExchange() {
        return new DirectExchange(GlobalConstants.ANIMAL_EXCHANGE_NAME);
    }

    @Bean
    public Binding animalStreamBindingMessages() {
        return BindingBuilder
                .bind(animalStreamQueue())
                .to(animalStreamExchange())
                .with(GlobalConstants.ANIMAL_ROUTING_KEY);
    }

    // DLQ / DLX configuration

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(GlobalConstants.DLX_NAME);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(GlobalConstants.DLQ_NAME).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }


    @Bean
    public AbstractMessageListenerContainer container(ConnectionFactory connectionFactory) {
        AbstractMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpAdmin amqpAdmin(final ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public AmqpTemplate amqpTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
