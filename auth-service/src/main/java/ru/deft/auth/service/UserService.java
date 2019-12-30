package ru.deft.auth.service;

import ru.deft.auth.dto.UserSaveDto;
import ru.deft.auth.dto.UserUpdateDto;

/*
 * Created by sgolitsyn on 12/23/19
 */
public interface UserService {

    Long createUser(UserSaveDto userSaveDto);

    Long updateUser(UserUpdateDto userUpdateDto);
}
