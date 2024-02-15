package org.example.producermodule.rabbitmq.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.example.producermodule.rabbitmq.service.MessageProducerWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RabbitMqController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RabbitMqControllerTest {
    @MockBean
    private MessageProducerWrapper messageProducerWrapper;

    @Autowired
    private RabbitMqController rabbitMqController;

    /**
     * Method under test: {@link RabbitMqController#produceCreate(AnimalDTO)}
     */
    @Test
    void testProduceCreate() throws Exception {
        // Arrange
        doNothing().when(messageProducerWrapper).produceDevMessages(any());

        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setAge("Age");
        animalDTO.setKindAnimal("Kind Animal");
        animalDTO.setName("Name");
        animalDTO.setTypePowerSupply("Type Power Supply");
        animalDTO.setVenomous("Venomous");
        String content = (new ObjectMapper()).writeValueAsString(animalDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/rabbitmq/produce/animal/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(rabbitMqController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link RabbitMqController#produceDelete(AnimalDeleteDTO)}
     */
    @Test
    void testProduceDelete() throws Exception {
        // Arrange
        doNothing().when(messageProducerWrapper).produceDevMessages(any());

        AnimalDeleteDTO animalDeleteDTO = new AnimalDeleteDTO();
        animalDeleteDTO.setId(1L);
        String content = (new ObjectMapper()).writeValueAsString(animalDeleteDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/rabbitmq/produce/animal/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(rabbitMqController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link RabbitMqController#produceUpdate(AnimalUpdateDTO)}
     */
    @Test
    void testProduceUpdate() throws Exception {
        // Arrange
        doNothing().when(messageProducerWrapper).produceDevMessages(any());

        AnimalUpdateDTO animalUpdateDTO = new AnimalUpdateDTO();
        animalUpdateDTO.setAge("Age");
        animalUpdateDTO.setId(1L);
        animalUpdateDTO.setKindAnimal("Kind Animal");
        animalUpdateDTO.setName("Name");
        animalUpdateDTO.setTypePowerSupply("Type Power Supply");
        animalUpdateDTO.setVenomous("Venomous");
        String content = (new ObjectMapper()).writeValueAsString(animalUpdateDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/rabbitmq/produce/animal/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(rabbitMqController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
