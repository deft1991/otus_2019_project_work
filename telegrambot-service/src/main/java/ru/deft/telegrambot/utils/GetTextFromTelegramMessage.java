package ru.deft.telegrambot.utils;

/*
 * Created by sgolitsyn on 12/20/19
 */
public interface GetTextFromTelegramMessage {

    static String getText(String[] strings) {

        if (strings == null || strings.length == 0) {
            return null;
        }

        String name = String.join(" ", strings);
        return name.replaceAll(" ", "").isEmpty() ? null : name;
    }
}
