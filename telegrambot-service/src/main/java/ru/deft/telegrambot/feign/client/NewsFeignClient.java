package ru.deft.telegrambot.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.deft.telegrambot.dto.RecommendDto;
import ru.deft.telegrambot.feign.config.FeignClientConfig;
import ru.deft.telegrambot.model.backendservice.News;

/*
 * Created by sgolitsyn on 12/24/19
 */
@FeignClient(value = "news", url = "http://localhost:8082/ui", configuration = FeignClientConfig.class)
public interface NewsFeignClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/news/publish")
    News geNewsForPublish();

    @RequestMapping(method = RequestMethod.POST, value = "/news/recommend")
    void recommendNews(RecommendDto recommendDto);
}
