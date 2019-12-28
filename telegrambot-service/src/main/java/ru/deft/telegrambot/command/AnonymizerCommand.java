package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Slf4j
abstract class AnonymizerCommand extends BotCommand {

    public static final String LOG_ANONYMIZER_COMMAND_SENDER = "AnonymizerCommand sender: %s with message: %s with user: %s";
    public static final String LOG_ERROR_ANONYMIZER_COMMAND_SENDER = "ERROR AnonymizerCommand sender: %s with message: %s with user: %s with error: %s";

    AnonymizerCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);
            log.debug(String.format(LOG_ANONYMIZER_COMMAND_SENDER, sender.toString(), message.getText(), user.getUserName()));
        } catch (TelegramApiException e) {
            log.error(String.format(LOG_ERROR_ANONYMIZER_COMMAND_SENDER, sender.toString(), message.getText(), user.getUserName(), e.getLocalizedMessage()));
        }
    }
}
