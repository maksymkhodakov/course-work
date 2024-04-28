package com.example.zoo.integrations.telegram.repository;

import com.example.zoo.integrations.telegram.domain.entities.TelegramUserLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramLogsRepository extends JpaRepository<TelegramUserLogs, Long> {
}
