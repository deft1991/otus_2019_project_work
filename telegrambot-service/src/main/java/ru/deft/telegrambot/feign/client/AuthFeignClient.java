package ru.deft.telegrambot.feign.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.deft.telegrambot.feign.config.FeignClientConfig;
import ru.deft.telegrambot.model.authservice.UserEntity;

/*
 * Created by sgolitsyn on 12/20/19
 */
@FeignClient(value = "auth", url = "http://localhost:8081/auth", configuration = FeignClientConfig.class)
public interface AuthFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/user/create")
    @Headers("Content-Type: application/json")
    Long createUser(UserEntity userEntity);
}
