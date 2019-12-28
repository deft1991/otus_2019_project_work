package ru.deft.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.deft.backend.dto.NewsDto;
import ru.deft.backend.dto.RecommendDto;
import ru.deft.backend.model.News;
import ru.deft.backend.repository.NewsRepository;
import ru.deft.backend.service.NewsService;

import java.security.Principal;
import java.util.UUID;

/*
 * Created by sgolitsyn on 12/17/19
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;


    @Override
    public UUID createNews(NewsDto dto, Principal principal) {
        News entity = dto.toEntity();
        entity.setCreatedBy(principal.getName());
        News news = newsRepository.save(entity);
        return news.getId();
    }

    @Override
    public UUID updateNews(NewsDto dto) {
        return null;
    }

    @Override
    public News getNewsById(UUID id) {
        return null;
    }

    @Override
    public Iterable<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News geNewsForPublish() {
        News news = newsRepository.geNewsForPublish();
        if (news != null) {
            news.setPublished(true);
            return newsRepository.save(news);
        } else {
            return null;
        }
    }

    @Override
    public void recommendNews(RecommendDto recommendDto) {
        newsRepository.save(recommendDto.toNewsEntity());
    }
}
