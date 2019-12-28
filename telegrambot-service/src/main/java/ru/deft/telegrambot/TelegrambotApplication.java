package ru.deft.telegrambot;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;


@Slf4j
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@RequiredArgsConstructor
public class TelegrambotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init(); // line for telegram bot https://github.com/rubenlagus/TelegramBots/wiki/How-To-Update#to-version-242
        SpringApplication.run(TelegrambotApplication.class, args);
    }
}
