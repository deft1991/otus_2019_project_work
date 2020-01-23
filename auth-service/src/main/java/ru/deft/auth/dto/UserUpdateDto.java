package ru.deft.auth.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * Created by sgolitsyn on 12/30/19
 */
@Getter
@Setter
public class UserUpdateDto {
    private Integer telegramId;
    private String nickName;
}
