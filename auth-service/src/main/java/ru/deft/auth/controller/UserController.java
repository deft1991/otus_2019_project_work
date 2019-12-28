package ru.deft.auth.controller;

/*
 * Created by sgolitsyn on 12/16/19
 */

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.deft.auth.dto.UserEntityDto;
import ru.deft.auth.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public Principal user(Principal principal) {
        return principal;
    }


    @PostMapping("/create")
    public Long user(@RequestBody UserEntityDto userEntityDto) {
        return userService.createUser(userEntityDto);
    }
}
