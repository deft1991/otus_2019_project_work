package ru.deft.auth.service.impl;

/*
 * Created by sgolitsyn on 12/23/19
 */

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.deft.auth.dto.UserEntityDto;
import ru.deft.auth.model.UserEntity;
import ru.deft.auth.repository.UserRepository;
import ru.deft.auth.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Long createUser(UserEntityDto userEntityDto) {
        String encodePass = passwordEncoder.encode(userEntityDto.getPassword());
        UserEntity save = userRepository.save(new UserEntity(userEntityDto.getUsername(), encodePass));
        return save.getId();
    }
}
