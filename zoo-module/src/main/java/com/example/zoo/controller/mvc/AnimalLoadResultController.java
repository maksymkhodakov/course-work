package com.example.zoo.controller.mvc;

import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.services.AnimalStreamLoadResultService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/animal-stream-loader")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnimalLoadResultController {
    public static final String REDIRECT_ANIMAL_LOAD_GET_ALL = "redirect:/animal-stream-loader/getAll";
    AnimalStreamLoadResultService animalStreamLoadResultService;

    @GetMapping("/getAll")
    public String getAll(Model model) {
        final var loadedResults = animalStreamLoadResultService.getAll();
        model.addAttribute("loadedFiles", loadedResults);
        return "indexAnimalStream";
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam Long id) {
        return animalStreamLoadResultService.getResource(id);
    }

    @PostMapping("/download-template")
    public ResponseEntity<byte[]> downloadTemplate() {
        return animalStreamLoadResultService.getTemplate();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        animalStreamLoadResultService.delete(id);
        return REDIRECT_ANIMAL_LOAD_GET_ALL;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("processTypes", AnimalStreamProcessType.values());
        return "createAnimalStreamLoad";
    }

    @PostMapping("/create")
    public String submitCreate(@RequestParam("processType") AnimalStreamProcessType processType,
                               @RequestParam("dataFile") MultipartFile file) {
        animalStreamLoadResultService.save(processType, file);
        return REDIRECT_ANIMAL_LOAD_GET_ALL;
    }
}
