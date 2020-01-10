package ru.deft.telegrambot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.deft.telegrambot.bot.AnonymizerBot;
import ru.deft.telegrambot.feign.client.NewsFeignClient;
import ru.deft.telegrambot.model.backendservice.News;

import java.util.List;

/*
 * newsletter - рассылка новостей
 * Created by sgolitsyn on 12/25/19
 */
@Slf4j
@Service
@EnableAsync // If we want to support parallel behavior in scheduled tasks,
// we need to add the @Async annotation
@RequiredArgsConstructor
public class NewsletterService {

    @Qualifier("chats")
    private final List<Chat> chats;
    private final AnonymizerBot anonymizerBot;
    private final NewsFeignClient newsFeignClient;

    @Async
    @Scheduled(cron = "*/15 * * * * ?")
    public void newsletter() {
        try {

            log.info("Try to get news for publish");
            News news = newsFeignClient.geNewsForPublish();
            if (news != null) {

                StringBuilder textMessage = new StringBuilder();
                textMessage.append(String.format("News from %s. \n", news.getUserName()));
                textMessage.append(String.format("%s. \n", news.getNewsText()));
                SendMessage message = new SendMessage();
                message.setText(textMessage.toString());
                chats.forEach(chat -> {
                    try {
                        message.setChatId(chat.getId());
                        anonymizerBot.execute(message);
                    } catch (TelegramApiException e) {
                        log.error(String.format("Error while NewsletterService.newsletter: %s", e.getLocalizedMessage()));
                        e.printStackTrace();
                    }
                });
            } else {
                log.debug("Haven`t new NEWS for post.");
            }

        } catch (Exception e) {
            log.error(String.format("Error in NewsletterService.newsletter: %s", e.getMessage()));
        }
    }
}
