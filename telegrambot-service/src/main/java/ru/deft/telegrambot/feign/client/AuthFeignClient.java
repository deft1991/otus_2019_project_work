package ru.deft.telegrambot.feign.client;

import feign.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.deft.telegrambot.feign.config.FeignClientConfig;
import ru.deft.telegrambot.model.authservice.UserEntity;

import java.util.UUID;

/*
 * Created by sgolitsyn on 12/20/19
 */
@FeignClient(value = "auth", url = "${feign.client.config.auth.url}", configuration = FeignClientConfig.class)
public interface AuthFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/user/create")
    @Headers("Content-Type: application/json")
    UUID createUser(UserEntity userEntity);

    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    @Headers("Content-Type: application/json")
    void updateUser(UserEntity userEntity);
}
