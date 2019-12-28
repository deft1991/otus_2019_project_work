package ru.deft.telegrambot.feign;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.deft.telegrambot.model.backendservice.News;

/*
 * Created by sgolitsyn on 12/20/19
 */
@Getter
@Setter
@NoArgsConstructor
public class BookResource {
    private News news;
}
