package ru.deft.telegrambot.model.authservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/*
 * Created by sgolitsyn on 12/11/19
 */

@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    private UUID id;
    private Integer telegramId;
    private String username;
    private String password;
    private String nickName;

    public UserEntity(String username, Integer telegramId, String password) {
        this.username = username;
        this.telegramId = telegramId;
        this.password = password;
    }

    public UserEntity(Integer telegramId, String displayedName) {
        this.telegramId = telegramId;
        this.nickName = displayedName;
    }
}
