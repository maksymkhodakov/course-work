package com.example.zoo.services.impl;

import com.example.zoo.dto.AnimalStreamLoadResultDTO;
import com.example.zoo.entity.AnimalStreamLoadResult;
import com.example.zoo.enums.AnimalStreamProcessType;
import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.example.zoo.repository.AnimalStreamLoadResultRepository;
import com.example.zoo.services.AnimalStreamLoadResultService;
import com.example.zoo.storage.config.AWSProperties;
import com.example.zoo.storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnimalStreamLoadResultServiceImpl implements AnimalStreamLoadResultService {
    private final AWSProperties awsProperties;
    private final S3Service s3Service;
    private final AnimalStreamLoadResultRepository animalStreamLoadResultRepository;

    @Override
    public List<AnimalStreamLoadResultDTO> getAll() {
        return animalStreamLoadResultRepository.findAll()
                .stream()
                .map(load -> AnimalStreamLoadResultDTO.builder()
                        .id(load.getId())
                        .processType(load.getProcessType())
                        .filename(load.getFilename())
                        .createDate(load.getCreateDate())
                        .isProcessed(load.isProcessed())
                        .build())
                .sorted(Comparator.comparing(AnimalStreamLoadResultDTO::getCreateDate, Comparator.reverseOrder()))
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final var loadToDelete = animalStreamLoadResultRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_LOAD_NOT_FOUND));

        final String s3Link = loadToDelete.getS3Link();

        animalStreamLoadResultRepository.delete(loadToDelete);

        if (Objects.nonNull(s3Link) && !s3Link.isEmpty()) {
            s3Service.deleteFile(awsProperties.getZooServiceAnimalStreamLoadBucketName(), s3Link);
        }
    }

    @Override
    @Transactional
    public void save(AnimalStreamProcessType processType, MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            throw new OperationException(ApiErrors.EMPTY_FILE);
        }

        final String filename = file.getOriginalFilename();
        final String s3Link = s3Service.uploadFile(awsProperties.getZooServiceAnimalStreamLoadBucketName(), file);

        final AnimalStreamLoadResult loadToSave = AnimalStreamLoadResult.builder()
                .processType(processType)
                .filename(filename)
                .s3Link(s3Link)
                .animalStreams(new ArrayList<>())
                .processed(false)
                .build();

        animalStreamLoadResultRepository.save(loadToSave);
    }

    @Override
    public ResponseEntity<byte[]> getResource(Long id) {
        final var load = animalStreamLoadResultRepository.findById(id)
                .orElseThrow(() -> new OperationException(ApiErrors.ANIMAL_LOAD_NOT_FOUND));

        final byte[] bytes = s3Service.downloadFile(awsProperties.getZooServiceAnimalStreamLoadBucketName(), load.getS3Link());

        return ResponseEntity.ok()
                .header("content-disposition", "attachment; filename=" + load.getFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<byte[]> getTemplate() {
        final Resource resource = new ClassPathResource("templates/template.xlsx");
        return ResponseEntity.ok()
                .header("content-disposition", "attachment; filename=" + resource.getFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource.getInputStream().readAllBytes());
    }
}
