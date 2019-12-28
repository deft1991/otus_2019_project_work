package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Slf4j
public class HelpCommand extends AnonymizerCommand {

    public static final String LOG_HELP_COMMAND = "HelpCommand with user: id = %s name = %s, commandIdentifier: %s";
    private final ICommandRegistry mCommandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "list all known commands\n");
        mCommandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        log.debug(String.format(LOG_HELP_COMMAND, user.getId(), user.getUserName(), getCommandIdentifier()));

        StringBuilder helpMessageBuilder = new StringBuilder("<b>Available commands:</b>");

        mCommandRegistry.getRegisteredCommands().forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        execute(absSender, helpMessage, user);
    }
}
