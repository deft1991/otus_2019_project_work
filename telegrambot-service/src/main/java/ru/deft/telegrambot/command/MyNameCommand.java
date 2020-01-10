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
@Component("MyNameCommand")
public class MyNameCommand extends AnonymizerCommand {
    public static final String LOG_MY_NAME_COMMAND = "MyNameCommand with user: id = %s name = %s, commandIdentifier: %s";

    private final AnonymousService mAnonymouses;

    @Autowired
    public MyNameCommand(@Qualifier("AnonymousService") AnonymousService anonymouses) {
        super("my_name", "show your current name that will be displayed with your messages\n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        log.debug(String.format(LOG_MY_NAME_COMMAND, user.getId(), user.getUserName(), getCommandIdentifier()));

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)) {

            sb.append("You are not in bot users' list! Send /start command!");
            log.warn(String.format("User %s is trying to execute '%s' without starting the bot.", user.getId(), getCommandIdentifier()));

        } else if (mAnonymouses.getDisplayedName(user) == null) {

            sb.append("Currently you don't have a name.\nSet it using command:\n'/set_name &lt;displayed_name&gt;'");
            log.warn(String.format("User %s is trying to execute '%s' without having a name.", user.getId(), getCommandIdentifier()));

        } else {

            log.info("User {} is executing '{}'. Name is '{}'.", user.getId(), getCommandIdentifier(), mAnonymouses.getDisplayedName(user));
            sb.append("Your current name: ").append(mAnonymouses.getDisplayedName(user));
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}
