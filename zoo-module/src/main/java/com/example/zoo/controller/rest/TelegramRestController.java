package com.example.zoo.controller.rest;

import com.example.zoo.dto.ResponseDTO;
import com.example.zoo.integrations.telegram.domain.entities.TelegramUserLogs;
import com.example.zoo.integrations.telegram.repository.TelegramLogsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/telegram-logs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramRestController {
    TelegramLogsRepository telegramLogsRep;

    @GetMapping("/getAll")
    @PreAuthorize("hasPermission(null, T(com.example.zoo.enums.Privilege).ROLE_BASIC_USER)")
    public ResponseDTO<List<TelegramUserLogs>> getAll() {
        return ResponseDTO.ofData(telegramLogsRep.findAll(), ResponseDTO.ResponseStatus.OK);
    }
}
