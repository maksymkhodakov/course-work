package org.example.producermodule.rabbitmq.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MessageProducerWrapperImpl.class, RabbitTemplate.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MessageProducerWrapperImplTest {
    @Autowired
    private MessageProducerWrapperImpl messageProducerWrapperImpl;

    @MockBean
    private RabbitTemplate rabbitTemplate;


    /**
     * Method under test:
     * {@link MessageProducerWrapperImpl#produceAnimalStreamMessages(Object)}
     */
    @Test
    void testProduceAnimalStreamMessages() throws AmqpException {
        // Arrange
        doNothing().when(rabbitTemplate)
                .convertAndSend(any(), any(), Mockito.<Object>any());

        // Act
        messageProducerWrapperImpl.produceAnimalStreamMessages("Object");

        // Assert that nothing has changed
        verify(rabbitTemplate).convertAndSend(eq("animal-stream"), eq("animal-stream"), Mockito.<Object>any());
    }

    /**
     * Method under test:
     * {@link MessageProducerWrapperImpl#produceDevMessages(Object)}
     */
    @Test
    void testProduceDevMessages() throws AmqpException {
        // Arrange
        doNothing().when(rabbitTemplate)
                .convertAndSend(any(), any(), Mockito.<Object>any());

        // Act
        messageProducerWrapperImpl.produceDevMessages("Object");

        // Assert that nothing has changed
        verify(rabbitTemplate).convertAndSend(eq("dev"), eq("dev"), Mockito.<Object>any());
    }
}
