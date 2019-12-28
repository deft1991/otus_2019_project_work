package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.deft.telegrambot.model.telegramservice.Anonymous;
import ru.deft.telegrambot.service.AnonymousService;

import java.util.List;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Slf4j
@Component("StartCommand")
public class StartCommand extends AnonymizerCommand {

    public static final String LOG_COMMAND_WITH_USER_ID_AND_COMMAND_IDENTIFIER = "Start execute command: %s, userId: %s, commandIdentifier: %s";


    private final AnonymousService mAnonymouses;
    private final List<Chat> chats;

    // обязательно нужно вызвать конструктор суперкласса,
    // передав в него имя и описание команды
    @Autowired
    public StartCommand(@Qualifier("AnonymousService") AnonymousService anonymouses
            , @Qualifier("chats") List<Chat> chats) {
        super("start", "start using bot\n");
        this.mAnonymouses = anonymouses;
        this.chats = chats;
    }

    /**
     * реализованный метод класса BotCommand, в котором обрабатывается команда, введенная пользователем
     *
     * @param absSender - отправляет ответ пользователю
     * @param user      - пользователь, который выполнил команду
     * @param chat      - чат бота и пользователя
     * @param strings   - аргументы, переданные с командой
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        log.info(String.format(LOG_COMMAND_WITH_USER_ID_AND_COMMAND_IDENTIFIER, "StartCommand", user.getId(), getCommandIdentifier()));

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (mAnonymouses.addAnonymous(new Anonymous(user, chat))) {
            log.info(String.format("User id = %s is trying to execute '%s' the first time. Added to users' list.", user.getId(), getCommandIdentifier()));
            sb.append("Hi, ").append(user.getUserName()).append("! You've been added to bot users' list!\n")
                    .append("Please execute command:\n'/set_name <displayed_name>'\nwhere &lt;displayed_name&gt; is the name you want to use to hide your real name.");
        } else {
            log.info(String.format("User id = %s has already executed '%s'. Is he trying to do it one more time?", user.getId(), getCommandIdentifier()));
            sb.append("You've already started bot! You can send messages if you set your name (/set_name).");
        }

        chats.add(chat);
        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}
