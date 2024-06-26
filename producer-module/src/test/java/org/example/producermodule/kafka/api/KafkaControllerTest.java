package org.example.producermodule.kafka.api;

import static org.mockito.Mockito.doNothing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.producermodule.dto.AnimalDTO;
import org.example.producermodule.dto.AnimalDeleteDTO;
import org.example.producermodule.dto.AnimalUpdateDTO;
import org.example.producermodule.kafka.config.TopicNameConfig;
import org.example.producermodule.kafka.service.KafkaSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ContextConfiguration(classes = {KafkaController.class, TopicNameConfig.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class KafkaControllerTest {
    @Autowired
    private KafkaController kafkaController;

    @MockBean
    private KafkaSenderService kafkaSenderService;

    @Autowired
    private TopicNameConfig topicNameConfig;

    /**
     * Method under test: {@link KafkaController#produceCreate(AnimalDTO)}
     */
    @Test
    void testProduceCreate() throws Exception {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setAge("Age");
        animalDTO.setKindAnimal("Kind Animal");
        animalDTO.setName("Name");
        animalDTO.setTypePowerSupply("Type Power Supply");
        animalDTO.setVenomous("Venomous");
        String content = (new ObjectMapper()).writeValueAsString(animalDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/kafka/produce/animal/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        doNothing().when(kafkaSenderService).produceMessages(topicNameConfig.getDevSave(), animalDTO);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(kafkaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link KafkaController#produceDelete(AnimalDeleteDTO)}
     */
    @Test
    void testProduceDelete() throws Exception {
        AnimalDeleteDTO animalDeleteDTO = new AnimalDeleteDTO();
        animalDeleteDTO.setId(1L);
        String content = (new ObjectMapper()).writeValueAsString(animalDeleteDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/kafka/produce/animal/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        doNothing().when(kafkaSenderService).produceMessages(topicNameConfig.getDevDelete(), animalDeleteDTO);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(kafkaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link KafkaController#produceUpdate(AnimalUpdateDTO)}
     */
    @Test
    void testProduceUpdate() throws Exception {
        AnimalUpdateDTO animalUpdateDTO = new AnimalUpdateDTO();
        animalUpdateDTO.setAge("Age");
        animalUpdateDTO.setId(1L);
        animalUpdateDTO.setKindAnimal("Kind Animal");
        animalUpdateDTO.setName("Name");
        animalUpdateDTO.setTypePowerSupply("Type Power Supply");
        animalUpdateDTO.setVenomous("Venomous");
        String content = (new ObjectMapper()).writeValueAsString(animalUpdateDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/kafka/produce/animal/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        doNothing().when(kafkaSenderService).produceMessages(topicNameConfig.getDevUpdate(), animalUpdateDTO);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(kafkaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
