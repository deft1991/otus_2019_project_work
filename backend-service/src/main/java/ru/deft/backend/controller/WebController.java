package ru.deft.backend.controller;

/*
 * Created by sgolitsyn on 12/16/19
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WebController {
    @GetMapping("/securedPage")
    public String securedPage(Model model, Principal principal) {
        return "securedPage.html";
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        return "index.html";
    }
}
