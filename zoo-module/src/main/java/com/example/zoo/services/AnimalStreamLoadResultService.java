package com.example.zoo.services;

import com.example.zoo.dto.AnimalStreamLoadResultDTO;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.enums.FileType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnimalStreamLoadResultService {
    List<AnimalStreamLoadResultDTO> getAll();

    void delete(Long id);

    void save(AnimalStreamProcessType processType, MultipartFile file);

    ResponseEntity<byte[]> getResource(Long id);

    ResponseEntity<byte[]> getTemplate(FileType fileType);
}
