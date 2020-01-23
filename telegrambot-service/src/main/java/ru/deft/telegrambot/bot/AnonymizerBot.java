package ru.deft.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.deft.telegrambot.command.HelpCommand;
import ru.deft.telegrambot.command.MyNameCommand;
import ru.deft.telegrambot.command.RecommendNewsCommand;
import ru.deft.telegrambot.command.SetNameCommand;
import ru.deft.telegrambot.command.StartCommand;
import ru.deft.telegrambot.command.StopCommand;
import ru.deft.telegrambot.model.telegramservice.Anonymous;
import ru.deft.telegrambot.service.AnonymousService;

import java.util.stream.Stream;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Slf4j
@Component("AnonymizerBot")
public final class AnonymizerBot extends TelegramLongPollingCommandBot {

    // имя бота, которое мы указали при создании аккаунта у BotFather
    // и токен, который получили в результате
    @Value("${telegram.botName}")
    private static final String BOT_NAME = "OtusChatWithTeacherBot";
    @Value("${telegram.botToken}")
    private static final String BOT_TOKEN = "821627745:AAES4-3c9qEvFtoiefr_gKNeSL0nTDSeba0";

    private final AnonymousService anonymousService;

    @Autowired
    public AnonymizerBot(
            @Qualifier("botOptions") DefaultBotOptions botOptions
            , @Qualifier("AnonymousService") AnonymousService anonymousService
            , @Qualifier("StartCommand") StartCommand startCommand
            , @Qualifier("SetNameCommand") SetNameCommand setNameCommand
            , @Qualifier("StopCommand") StopCommand stopCommand
            , @Qualifier("MyNameCommand") MyNameCommand myNameCommand
            , @Qualifier("RecommendNewsCommand") RecommendNewsCommand recommendNewsCommand) {

        super(botOptions, BOT_NAME);
        this.anonymousService = anonymousService;

        log.info("Initializing Anonymizer Bot...");

        log.info("Initializing anonymouses list...");

        // регистрация всех кастомных команд
        log.info("Registering commands...");
        log.info("Registering '/start'...");
        register(startCommand);
        log.info("Registering '/set_name'...");
        register(setNameCommand);
        log.info("Registering '/stop'...");
        register(stopCommand);
        log.info("Registering '/my_name'...");
        register(myNameCommand);
        HelpCommand helpCommand = new HelpCommand(this);
        log.info("Registering '/help'...");
        register(helpCommand);
        log.info("Registering '/recommend'...");
        register(recommendNewsCommand);

        // обработка неизвестной команды
        log.info("Registering default action'...");
        registerDefaultAction(((absSender, message) -> {

            log.info(String.format("User id = %s is trying to execute unknown command '%s'.", message.getFrom().getId(), message.getText()));

            SendMessage text = new SendMessage();
            text.setChatId(message.getChatId());
            text.setText(message.getText() + " command not found!");

            try {
                absSender.execute(text);
            } catch (TelegramApiException e) {
                log.error("Error while replying unknown command to user {}.", message.getFrom(), e);
            }

            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        }));

        this.init();
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    // обработка сообщения не начинающегося с '/'
    @Override
    public void processNonCommandUpdate(Update update) {

        log.info("Processing non-command update...");

        if (!update.hasMessage()) {
            log.error("Update doesn't have a body!");
            throw new IllegalStateException("Update doesn't have a body!");
        }

        Message msg = update.getMessage();
        User user = msg.getFrom();

        log.info(String.format("Message processing user id = %s", user.getId()));

        if (!canSendMessage(user, msg)) {
            return;
        }

        String clearMessage = msg.getText();
        String messageForUsers = String.format("%s:\n%s", anonymousService.getDisplayedName(user), msg.getText());

        SendMessage answer = new SendMessage();

        // отправка ответа отправителю о том, что его сообщение получено
        answer.setText(clearMessage);
        answer.setChatId(msg.getChatId());
        replyToUser(answer, user, clearMessage);

        // отправка сообщения всем остальным пользователям бота
        answer.setText(messageForUsers);
        Stream<Anonymous> anonymouses = anonymousService.anonymouses();
        anonymouses.filter(a -> !a.getUser().equals(user))
                .forEach(a -> {
                    answer.setChatId(a.getChat().getId());
                    sendMessageToUser(answer, a.getUser(), user);
                });
    }

    // несколько проверок, чтобы можно было отправлять сообщения другим пользователям
    private boolean canSendMessage(User user, Message msg) {

        SendMessage answer = new SendMessage();
        answer.setChatId(msg.getChatId());

        if (!msg.hasText() || msg.getText().trim().length() == 0) {
            log.info(String.format("User id = %s is trying to send empty message!", user.getId()));
            answer.setText("You shouldn't send empty messages!");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        if (!anonymousService.hasAnonymous(user)) {
            log.info(String.format("User id = %s is trying to send message without starting the bot!", user.getId()));
            answer.setText("Firstly you should start bot! Use /start command!");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        if (anonymousService.getDisplayedName(user) == null) {
            log.info(String.format("User id = %s is trying to send message without setting a name!", user.getId()));
            answer.setText("You must set a name before sending messages.\nUse '/set_name <displayed_name>' command.");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        return true;
    }

    private void sendMessageToUser(SendMessage message, User receiver, User sender) {
        try {
            execute(message);
            log.info(String.format("SUCCESS sendMessageToUser --> receiver id = %s , sender id = %s", receiver.getId(), sender.getId()));
        } catch (TelegramApiException e) {
            log.error(String.format("Error while sendMessageToUser with receiver id = %s, sender id = %s", receiver.getId(), sender.getId()), e);
        }
    }

    private void replyToUser(SendMessage message, User user, String messageText) {
        try {
            execute(message);
            log.info(String.format("Success replyToUser: user id = %s, messageText = %s", user.getId(), messageText));
        } catch (TelegramApiException e) {
            log.error(String.format("Error while replyToUser: user id = %s", user.getId()), e);
        }
    }

    public void init() {
        try {

            log.info("Initializing API context...");
            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

            log.info("Configuring bot options...");

//            this.botOptions = botOptions;
            log.info("Registering Anonymizer...");
            botsApi.registerBot(this);

            log.info("Anonymizer bot is ready for work!");

        } catch (TelegramApiRequestException e) {
            log.error("Error while initializing bot!", e);
        }
    }
}
