package ru.deft.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.deft.auth.model.UserEntity;
import ru.deft.auth.repository.UserRepository;

/*
 * Created by sgolitsyn on 12/24/19
 */
@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        userRepository.save(new UserEntity("lala", passwordEncoder.encode("password")));
        userRepository.save(new UserEntity("user", passwordEncoder.encode("user")));
        userRepository.save(new UserEntity("admin", passwordEncoder.encode("admin")));
    }
}
