package com.example.zoo.controller.mvc;

import com.example.zoo.services.FailureStreamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/animal-stream")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnimalStreamController {
    public static final String REDIRECT_ANIMAL_STREAM_UNPROCESSED = "redirect:/animal-stream/getUnprocessed";
    FailureStreamService failureStreamService;

    @GetMapping("/getUnprocessed")
    public String getAll(Model model) {
        final var unprocessedStreams = failureStreamService.getUnprocessed();
        model.addAttribute("streams", unprocessedStreams);
        return "indexAnimalStreamUnprocessed";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        failureStreamService.delete(id);
        return REDIRECT_ANIMAL_STREAM_UNPROCESSED;
    }
}
