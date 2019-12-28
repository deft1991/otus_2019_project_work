package ru.deft.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.deft.backend.model.News;

/*
 * Created by sgolitsyn on 12/24/19
 */
@Getter
@Setter
@NoArgsConstructor
public class RecommendDto {
    String newsText, userFirstName;

    public RecommendDto(String newsText, String userFirstName) {
        this.newsText = newsText;
        this.userFirstName = userFirstName;
    }

    public News toNewsEntity() {
        News news = new News();
        news.setUserName(userFirstName);
        news.setNewsText(newsText);
        news.setCreatedBy(userFirstName);
        return news;
    }

}
