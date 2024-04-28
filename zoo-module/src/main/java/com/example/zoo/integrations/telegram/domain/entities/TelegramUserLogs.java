package com.example.zoo.integrations.telegram.domain.entities;


import com.example.zoo.entity.TimestampEntity;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class TelegramUserLogs extends TimestampEntity {
    private Long chatId;
    private String firstName;
    private String lastName;
    private String username;
    private String request;
    private String response;
}
