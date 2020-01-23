package ru.deft.telegrambot.config;

/*
 * Created by sgolitsyn on 11/22/19
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "telegram")
@Getter
@Setter
public class BotConfig {

    private String botName;
    private String botToken;
    private List<Chat> chats;
    private Proxy proxy;

    @Getter
    @Setter
    public static class Proxy {
        private String host;
        private Integer port;
    }

    private static final DefaultBotOptions.ProxyType PROXY_TYPE = DefaultBotOptions.ProxyType.HTTP;

    @Bean("botOptions")
    DefaultBotOptions botOptions() {

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(proxy.getHost());
        botOptions.setProxyPort(proxy.getPort());
        botOptions.setProxyType(PROXY_TYPE);
        return botOptions;
    }

    @Bean("chats")
    List<Chat> chats() {
        return new ArrayList<Chat>();
    }

}
