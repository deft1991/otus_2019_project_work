package ru.deft.auth.dto;

import lombok.Getter;
import lombok.Setter;
import ru.deft.auth.model.UserEntity;

/*
 * Created by sgolitsyn on 12/11/19
 */
@Getter
@Setter
public class UserSaveDto {
    Integer telegramId;
    private String username;
    private String password;

    public UserEntity toEntity() {
//        return new UserEntity(telegramId, username, password);
        return new UserEntity();
    }
}
