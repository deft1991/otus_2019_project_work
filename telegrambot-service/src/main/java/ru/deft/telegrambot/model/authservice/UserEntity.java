package ru.deft.telegrambot.model.authservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Created by sgolitsyn on 12/11/19
 */

@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    Long id;
    private String username;
    private String password;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
