package ru.deft.auth.service;

import ru.deft.auth.dto.UserSaveDto;
import ru.deft.auth.dto.UserUpdateDto;

import java.util.UUID;

/*
 * Created by sgolitsyn on 12/23/19
 */
public interface UserService {

    UUID createUser(UserSaveDto userSaveDto);

    UUID updateUser(UserUpdateDto userUpdateDto);
}
