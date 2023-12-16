package com.example.news;

import org.springframework.data.repository.CrudRepository;
import com.example.news.NewNewsConroller;
public interface PostRepository extends CrudRepository<Post, Long> {
}
