package com.example.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public class NewsService {
        @Autowired
        private static PostRepository postRepository;

        public Iterable<Post> getAllNews() {
            return postRepository.findAll();
        }

        public Post getNewsById(Long id) {
            return postRepository.findById(id).orElse(null);
        }

        public Post saveNews(Post news) {
            return postRepository.save(news);
        }

        public void deleteNewsById(Long id) {
            postRepository.deleteById(id);
        }
    public  Iterable<Post> searchByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }

}

