package ru.deft.auth.service.impl;

/*
 * Created by sgolitsyn on 12/23/19
 */

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.auth.dto.UserSaveDto;
import ru.deft.auth.dto.UserUpdateDto;
import ru.deft.auth.model.UserEntity;
import ru.deft.auth.repository.UserRepository;
import ru.deft.auth.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UUID createUser(UserSaveDto userSaveDto) {
        String encodePass = passwordEncoder.encode(userSaveDto.getPassword());
        UserEntity save = userRepository.save(new UserEntity(userSaveDto.getUsername(), userSaveDto.getTelegramId(), encodePass));
        return save.getId();
    }

    @Override
    @Transactional
    public UUID updateUser(UserUpdateDto userUpdateDto) {
        UserEntity byTelegramId = userRepository.findByTelegramId(userUpdateDto.getTelegramId());
        byTelegramId.setNickName(userUpdateDto.getNickName());
        return byTelegramId.getId();
    }
}
