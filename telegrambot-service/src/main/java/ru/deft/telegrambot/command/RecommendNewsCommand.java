package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.deft.telegrambot.dto.RecommendDto;
import ru.deft.telegrambot.feign.client.NewsFeignClient;
import ru.deft.telegrambot.service.AnonymousService;
import ru.deft.telegrambot.utils.GetTextFromTelegramMessage;

/*
 * Created by sgolitsyn on 12/20/19
 */
@Slf4j
@Component("RecommendNewsCommand")
public class RecommendNewsCommand extends AnonymizerCommand {

    public static final String LOG_RECOMMEND_COMMAND = "Start execute command %s with user: id = %s name = %s, commandIdentifier: %s";

    private final AnonymousService mAnonymouses;
    private final NewsFeignClient newsFeignClient;

    @Autowired
    public RecommendNewsCommand(@Qualifier("AnonymousService") AnonymousService mAnonymouses
            , NewsFeignClient newsFeignClient) {
        super("recommend", "recommend news for post in all chats\n");
        this.mAnonymouses = mAnonymouses;
        this.newsFeignClient = newsFeignClient;
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.debug(String.format(LOG_RECOMMEND_COMMAND, "RecommendNewsCommand", user.getId(), user.getUserName(), getCommandIdentifier()));

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)) {
            log.info(String.format("User id = %s  is trying to execute '%s' without starting the bot!", user.getId(), getCommandIdentifier()));
            message.setText("Firstly you should start the bot! Execute '/start' command!");
            execute(absSender, message, user);
            return;
        }
        if (mAnonymouses.getDisplayedName(user) == null) {
            log.info(String.format("User id = %s name can`t be empty", user.getId()));
            message.setText("You should use non-empty name! Use /set_name command!");
            execute(absSender, message, user);
            return;
        }

        String newsText = GetTextFromTelegramMessage.getText(arguments);
        newsFeignClient.recommendNews(new RecommendDto(newsText, user.getFirstName()));

        message.setText("You`ve recommended news! Congratulations!");
        execute(absSender, message, user);
    }
}
