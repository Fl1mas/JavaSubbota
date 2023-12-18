package com.example.news;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NewNewsConroller {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/news")
    public String newsMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "news-main";
    }

    @GetMapping("/news/add")
    public String newsAdd(Model model) {
        return "news-add";
    }

    @PostMapping("news/add")
    public String newsPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/news-main";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "newsdetails";
    }

    @GetMapping("/news/{id}/edit")
    public String newsEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "news-edit";
    }
    @PostMapping("news/{id}/edit")
    public String newsPostUpdate(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/news";
    }
    @GetMapping("news/{id}/remove")
    public String newsPostDelete(@PathVariable(value = "id") Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/news";
    }
}
