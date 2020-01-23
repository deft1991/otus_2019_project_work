package ru.deft.telegrambot.model.backendservice;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * Created by sgolitsyn on 12/17/19
 */
@Entity
@Table(name = "NEWS")
@Getter
@Setter
@NoArgsConstructor
public class News extends BaseEntity {
    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    @Column(name = "NEWS_TEXT", nullable = false)
    private String newsText;
    @Column(name = "LIKE_COUNT")
    private Integer likeCount;
    @Column(name = "DISLIKE")
    private Integer disLike;

}
