package com.example.zoo.integrations.telegram.service;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.Zoo;
import com.example.zoo.integrations.telegram.domain.dto.ZooTelegramDTO;
import com.example.zoo.integrations.telegram.domain.entities.TelegramUserLogs;
import com.example.zoo.integrations.telegram.domain.enums.MessageStatus;
import com.example.zoo.integrations.telegram.repository.TelegramLogsRepository;
import com.example.zoo.mapper.AnimalMapper;
import com.example.zoo.repository.ZooRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class TelegramService extends TelegramLongPollingBot {
    private final List<BotCommand> botCommands;
    private final TelegramLogsRepository telegramLogsRepository;
    private final ZooRepository zooRepository;
    private final AnimalMapper animalMapper;

    @Value("${telegram.bot.name}")
    private String name;

    @Value("${telegram.bot.token}")
    private String token;

    @PostConstruct
    private void setCommandsToMyBot() {
        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Telegram API error occurred: {}", e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final var message = update.getMessage();
            final var text = message.getText();
            switch (text) {
                case "/start" -> answer(message, MessageStatus.WELCOME_MESSAGE.getMessage());
                case "/help" -> answer(message, MessageStatus.HELP_MESSAGE.getMessage());
                case "/link" -> answer(message, "t.me/" + name);
                case "/zoo" -> getZoo(message);
                default -> answer(message, MessageStatus.DEFAULT_MESSAGE.getMessage());
            }
        }
    }

    private void getZoo(Message message) {
        final List<ZooTelegramDTO> payloads = zooRepository
                .findAll()
                .stream()
                .map(this::entityToTelegramDTO)
                .toList();
        payloads.forEach(payload -> {
            String answer = getAnswerFromPayload(payload);
            answer(message, answer);
            sendPhotos(message, payload);
        });
    }

    private void sendPhotos(Message message, ZooTelegramDTO payload) {
        payload.getPhotos()
                .forEach(photo -> {
                    InputFile inputFile = new InputFile(new ByteArrayInputStream(photo), "file");

                    final SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId().toString());
                    sendPhoto.setPhoto(inputFile);

                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        saveLogs(message, MessageStatus.ERROR_MESSAGE.getMessage());
                    }
                });
    }

    public ZooTelegramDTO entityToTelegramDTO(Zoo zoo) {
        return ZooTelegramDTO.builder()
                .name(zoo.getName())
                .countryName(Objects.isNull(zoo.getLocation()) ? "NO DATA" : zoo.getLocation().getName())
                .square(zoo.getSquare())
                .animalNames(zoo.getAnimals().stream().map(Animal::getName).toList())
                .photos(zoo.getAnimals().stream().map(animalMapper::getBytes).toList())
                .build();
    }

    private String getAnswerFromPayload(ZooTelegramDTO payload) {
        if (Objects.isNull(payload)) {
            return MessageStatus.NO_CONTENT.getMessage();
        }
        return generateMessage(payload);
    }

    private String generateMessage(ZooTelegramDTO p) {
        return "\nNAME: " + p.getName() + "\nCOUNTRY: " + p.getCountryName() + "\nSQUARE: " + p.getSquare() +
                "\nANIMAL NAMES: " + String.join(", ", p.getAnimalNames()) + "\nANIMAL PHOTOS:" + "\n";
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void answer(Message message, String answer) {
        SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()), answer);
        try {
            log.info("Message: " + answer + " \n Was sent to user: " + message.getChat().getUserName());
            execute(sendMessage);
            saveLogs(message, MessageStatus.SUCCESS_MESSAGE.getMessage());
        } catch (TelegramApiException e) {
            saveLogs(message, MessageStatus.ERROR_MESSAGE.getMessage());
        }
    }

    public void saveLogs(Message message, String response) {
        var logData = TelegramUserLogs.builder()
                .chatId(message.getChatId())
                .firstName(message.getChat().getFirstName())
                .lastName(message.getChat().getLastName())
                .username(message.getChat().getUserName())
                .request(message.getText())
                .response(response)
                .build();
        final var logSaved = telegramLogsRepository.saveAndFlush(logData);
        log.info(String.format("Telegram Log with id: %s was created", logSaved.getId()));
    }
}
