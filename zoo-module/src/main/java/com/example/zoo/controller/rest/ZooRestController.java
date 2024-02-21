package com.example.zoo.controller.rest;

import com.example.zoo.data.ZooData;
import com.example.zoo.dto.AnimalDTO;
import com.example.zoo.dto.ResponseDTO;
import com.example.zoo.dto.SearchDTO;
import com.example.zoo.dto.ZooDTO;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.services.ZooService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zoo")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ZooRestController {
    ZooService zooService;

    @GetMapping("/getAll")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<List<ZooDTO>> getAll() {
        return ResponseDTO.ofData(zooService.getAll(), ResponseDTO.ResponseStatus.OK);
    }

    @GetMapping("/pagination/getAll")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Page<ZooDTO>> paginationGetAll(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.ofData(zooService.getAll(searchDTO), ResponseDTO.ResponseStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<ZooDTO> getById(@PathVariable Long id) {
        try {
            return ResponseDTO.ofData(zooService.getById(id), ResponseDTO.ResponseStatus.OK);
        } catch (OperationException e) {
            return ResponseDTO.ofData(null, ResponseDTO.ResponseStatus.ERROR);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Void> create(@RequestPart("data") @Valid ZooData zooData) {
        try {
            zooService.save(zooData);
        } catch (OperationException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }

    @PutMapping("/update")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Void> update(@RequestPart("id") Long id,
                                    @RequestPart("data") @Valid ZooData zooData) {
        try {
            zooService.update(id, zooData);
        } catch (OperationException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Void> delete(@RequestParam Long id) {
        try {
            zooService.delete(id);
        } catch (OperationException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }

    @GetMapping("/animals/{id}")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<List<AnimalDTO>> getAllAnimals(@PathVariable Long id) {
        try {
            return ResponseDTO.ofData(zooService.getAllAnimals(id), ResponseDTO.ResponseStatus.OK);
        } catch (OperationException e) {
            return ResponseDTO.ofData(null, ResponseDTO.ResponseStatus.ERROR);
        }
    }

    @PostMapping("/addAnimal")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Void> addAnimal(@RequestParam("zooId") Long id,
                                       @RequestParam("animalId") Long animalId) {
        try {
            zooService.addAnimal(id, animalId);
        } catch (OperationException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }

    @DeleteMapping("/deleteAnimal/{animalId}/{zooId}")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Void> deleteAnimal(@PathVariable Long animalId,
                                          @PathVariable Long zooId) {
        try {
            zooService.deleteAnimal(zooId, animalId);
        } catch (OperationException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }
}
