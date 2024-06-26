package com.example.zoo.controller.rest;

import com.example.zoo.data.CountryData;
import com.example.zoo.dto.CountryDTO;
import com.example.zoo.dto.ResponseDTO;
import com.example.zoo.dto.SearchDTO;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.services.CountryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CountryRestController {
    CountryService countryService;

    @GetMapping("/getAll")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<List<CountryDTO>> getAll() {
        return ResponseDTO.ofData(countryService.getAll(), ResponseDTO.ResponseStatus.OK);
    }

    @GetMapping("/pagination/getAll")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Page<CountryDTO>> paginationGetAll(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.ofData(countryService.getAll(searchDTO), ResponseDTO.ResponseStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<CountryDTO> getById(@PathVariable Long id) {
        try {
            return ResponseDTO.ofData(countryService.getById(id), ResponseDTO.ResponseStatus.OK);
        } catch (OperationException e) {
            return ResponseDTO.ofData(null, ResponseDTO.ResponseStatus.ERROR);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Void> create(@RequestPart("data") @Valid CountryData countryData,
                                    @RequestPart("file") MultipartFile multipartFile) {
        try {
            countryService.save(countryData, multipartFile);
        } catch (IOException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }

    @PutMapping("/update")
    public ResponseDTO<Void> update(@RequestPart("id") Long id,
                                    @RequestPart("data") @Valid CountryData countryData,
                                    @RequestPart("file") MultipartFile multipartFile) {
        try {
            countryService.update(id, countryData, multipartFile);
        } catch (IOException | OperationException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<Void> delete(@RequestParam Long id) {
        try {
            countryService.delete(id);
        } catch (OperationException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok();
    }
}
