package org.example.producermodule.controller;

import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.enums.KindAnimal;
import com.example.zoo.enums.TypePowerSupply;
import com.example.zoo.repository.AnimalStreamRepository;
import lombok.RequiredArgsConstructor;
import org.example.producermodule.enums.EventType;
import org.example.producermodule.service.ProducerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerControllerMVC {
    private final AnimalStreamRepository animalStreamRepository;
    private final ProducerService producerService;

    @GetMapping("/getAll")
    public String getAll(Model model) {
        final var loadedResults = animalStreamRepository.findAll();
        model.addAttribute("streams", loadedResults);
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
}
