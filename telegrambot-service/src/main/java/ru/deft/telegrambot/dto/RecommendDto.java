package ru.deft.telegrambot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
