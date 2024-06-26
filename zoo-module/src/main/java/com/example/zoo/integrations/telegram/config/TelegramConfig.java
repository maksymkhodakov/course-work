package com.example.zoo.integrations.telegram.config;

import com.example.zoo.integrations.telegram.service.TelegramService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Configuration
@ConditionalOnProperty(value="telegram.bot.enabled")
public class TelegramConfig {
    @Bean
    public List<BotCommand> botCommands() {
        return List.of(
                new BotCommand("/start", "Welcome"),
                new BotCommand("/help", "Help"),
                new BotCommand("/link", "Link"),
                new BotCommand("/zoo", "All zoo"));
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramService telegramService) throws TelegramApiException {
        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramService);
        return telegramBotsApi;
    }
}
