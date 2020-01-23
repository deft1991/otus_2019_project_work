package ru.deft.telegrambot.model.telegramservice;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Getter
@Setter
@Slf4j
public final class Anonymous {

    private static final String USER_CHAT_CANNOT_BE_NULL = "User or chat cannot be null!";

    private final User user;
    private final Chat chat;
    private String displayedName;

    public Anonymous(User user, Chat chat) {
        if (user == null || chat == null) {
            log.error(USER_CHAT_CANNOT_BE_NULL);
            throw new IllegalStateException(USER_CHAT_CANNOT_BE_NULL);
        }
        this.user = user;
        this.chat = chat;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Anonymous && ((Anonymous) obj).getUser().equals(user);
    }
}
