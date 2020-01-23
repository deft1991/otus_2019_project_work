package ru.deft.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import ru.deft.backend.dto.NewsDto;
import ru.deft.backend.dto.NewsForm;
import ru.deft.backend.dto.RecommendDto;
import ru.deft.backend.model.News;
import ru.deft.backend.service.NewsService;

import java.security.Principal;
import java.util.UUID;

/*
 * Created by sgolitsyn on 12/17/19
 */
@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/{id}")
    public News getNewsById(@PathVariable UUID id) {
        return newsService.getNewsById(id);
    }

    @GetMapping
    public String getAllNews(Model model, Principal principal) {
        Iterable<News> allNews = newsService.getAllNews();
        model.addAttribute("newsList", allNews);
        return "newsList.html";
    }

    @GetMapping("/addNews")
    public String getNews(Model model, Principal principal) {
        NewsForm newsForm = new NewsForm();
        model.addAttribute("newsForm", newsForm);
        return "addNews.html";
    }

    @PostMapping("/addNews")
    public RedirectView createNews(Model model, @ModelAttribute("newsForm") NewsForm newsForm, Principal principal) {
        newsService.createNews(
                new NewsDto() {
                    {
                        setUserName(principal.getName());
                        setNewsText(newsForm.getNewsText());
                    }
                }, principal);
        return new RedirectView("");
    }

    @PutMapping
    public UUID updateNews(@RequestBody NewsDto newsDto) {
        return newsService.updateNews(newsDto);
    }

    @PutMapping("/publish")
    @ResponseBody
    public News geNewsForPublish() {
        return newsService.geNewsForPublish();
    }

    @PostMapping("/recommend")
    @ResponseBody
    public Boolean recommendNews(@RequestBody RecommendDto recommendDto) {
        newsService.recommendNews(recommendDto);
        return true;
    }
}
