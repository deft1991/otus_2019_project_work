package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.deft.telegrambot.service.AnonymousService;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Slf4j
@Component("StopCommand")
public class StopCommand extends AnonymizerCommand {

    public static final String LOG_COMMAND_WITH_USER_ID_AND_COMMAND_IDENTIFIER = "Start execute command: %s, userId: %s, commandIdentifier: %s";

    private final AnonymousService mAnonymouses;

    @Autowired
    public StopCommand(@Qualifier("AnonymousService") AnonymousService anonymouses) {
        super("stop", "remove yourself from bot users' list\n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        log.info(String.format(LOG_COMMAND_WITH_USER_ID_AND_COMMAND_IDENTIFIER, "StartCommand", user.getId(), getCommandIdentifier()));

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (mAnonymouses.removeAnonymous(user)) {
            log.info("User {} has been removed from users list!", user.getId());
            sb.append("You've been removed from bot's users list! Bye!");
        } else {
            log.info(String.format("User id = %s is trying to execute '%s' without having executed 'start' before!", user.getId(), getCommandIdentifier()));
            sb.append("You were not in bot users' list. Bye!");
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}
