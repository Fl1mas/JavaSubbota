package com.example.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestParam;

@Controller
    @RestController
    @RequestMapping("/api/news")
    public class NewsController {

        @Autowired
        private NewsService newsService;
        @GetMapping("/")

        public String index(Model model) {
            model.addAttribute("title","Цыдыпов Эдуард");
            return "index";
        }
        @GetMapping("/about")
        public String about(Model model) {
            model.addAttribute("title", "Страница про Эдуарда");
            return "about";
        }

        @GetMapping("/all")
        public Iterable<Post> getAllNews() {
            return newsService.getAllNews();
        }

        @GetMapping("/{id}")
        public Post getNewsById(@PathVariable Long id) {
            return newsService.getNewsById(id);
        }

        @PutMapping("/update/{id}")
        public Post updateNews(@PathVariable Long id, @RequestBody Post news) {
            news.setId(id);
            return newsService.saveNews(news);
        }

        @DeleteMapping("/delete/{id}")
        public void deleteNews(@PathVariable Long id) {
            newsService.deleteNewsById(id);
        }
    }

