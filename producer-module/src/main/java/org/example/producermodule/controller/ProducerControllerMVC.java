package org.example.producermodule.controller;

import com.example.zoo.entity.AnimalStream;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.enums.KindAnimal;
import com.example.zoo.enums.TypePowerSupply;
import com.example.zoo.repository.AnimalStreamRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.producermodule.enums.EventType;
import org.example.producermodule.service.ProducerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerControllerMVC {
    private final AnimalStreamRepository animalStreamRepository;
    private final ProducerService producerService;

    @GetMapping("/getAll")
    public String getAll(Model model) {
        final var streams = animalStreamRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(AnimalStream::getLastUpdateDate, Comparator.reverseOrder()))
                .map(stream -> StreamData.builder()
                        .id(stream.getId())
                        .name(stream.getName())
                        .venomous(stream.getVenomous())
                        .typePowerSupply(stream.getTypePowerSupply())
                        .age(stream.getAge())
                        .processed(stream.getProcessed())
                        .errorMessage(stream.getErrorMessage())
                        .processType(stream.getProcessType())
                        .build())
                .collect(Collectors.toSet());
        model.addAttribute("streams", streams);
        return "producerresult";
    }

    @GetMapping("/create-event")
    public String getCreateEvent(Model model) {
        model.addAttribute("broker", AnimalStreamProcessType.values());
        model.addAttribute("type", EventType.values());
        model.addAttribute("kind", KindAnimal.values());
        model.addAttribute("supply", TypePowerSupply.values());
        return "createProducerEvent";
    }

    @PostMapping("/create-event")
    public String createEvent(@RequestParam("typeInput") EventType eventType,
                              @RequestParam("brokerInput") AnimalStreamProcessType processType,
                              @RequestParam(value = "idInput") Integer id,
                              @RequestParam(value = "ageInput") Integer age,
                              @RequestParam("nameInput") String name,
                              @RequestParam("kindAnimal") KindAnimal kindAnimal,
                              @RequestParam(value = "venomous", defaultValue = "false") boolean venomous,
                              @RequestParam("typePowerSupply") TypePowerSupply typePowerSupply) {
        if (eventType == EventType.CREATE) {
            producerService.handleSave(processType, age, name, kindAnimal, venomous, typePowerSupply);
        }
        if (eventType == EventType.UPDATE) {
            producerService.handleUpdate(processType, id, age, name, kindAnimal, venomous, typePowerSupply);
        }
        if (eventType == EventType.DELETE) {
            producerService.handleDelete(processType, id);
        }
        return "redirect:/producer/getAll";
    }

    @Data
    @Builder
    private static class StreamData {
        private Long id;
        private String name;
        private String kindAnimal;
        private String venomous;
        private String typePowerSupply;
        private String age;
        private Boolean processed;
        private String errorMessage;
        private AnimalStreamProcessType processType;
    }
}
