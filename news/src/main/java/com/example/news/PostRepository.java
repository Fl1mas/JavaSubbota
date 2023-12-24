package com.example.news;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.news.NewNewsConroller;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findByTitleContaining(String title);
    Post getNewsById(Long id);
}
//public interface PostRepository extends JpaRepository<Post, Long> {}