package ru.deft.backend.service;

import ru.deft.backend.dto.NewsDto;
import ru.deft.backend.dto.RecommendDto;
import ru.deft.backend.model.News;

import java.security.Principal;
import java.util.UUID;

/*
 * Created by sgolitsyn on 12/17/19
 */
public interface NewsService {

    UUID createNews(NewsDto dto, Principal principal);

    UUID updateNews(NewsDto dto);

    News getNewsById(UUID id);

    Iterable<News> getAllNews();

    News geNewsForPublish();

    void recommendNews(RecommendDto recommendDto);
}
