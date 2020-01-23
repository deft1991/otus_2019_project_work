package ru.deft.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.deft.auth.model.UserEntity;

import java.util.UUID;

/*
 * Created by sgolitsyn on 12/23/19
 */
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByTelegramId(Integer telegramId);
}
