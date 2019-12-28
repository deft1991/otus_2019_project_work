package ru.deft.telegrambot.repository;

/*
 * Created by sgolitsyn on 12/17/19
 */

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.deft.telegrambot.model.backendservice.News;

import java.util.UUID;

@Repository
public interface NewsRepository extends CrudRepository<News, UUID> {

}
