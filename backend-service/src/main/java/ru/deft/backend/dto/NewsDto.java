package ru.deft.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.deft.backend.model.News;

/*
 * Created by sgolitsyn on 12/17/19
 */
@NoArgsConstructor
@Getter
@Setter
public class NewsDto {
    private String userName;
    private String newsText;
    private int likeCount;
    private int disLike;

    public News toEntity() {
        News news = new News();
        news.setUserName(this.userName);
        news.setNewsText(this.newsText);
        return news;
    }
}
