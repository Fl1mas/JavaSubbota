package com.example.news;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class PostRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void findByTitleContaining_ShouldReturnPosts() {
        String title = "testTitle";
        Post post = new Post(title, "anons", "full_text");
        entityManager.persist(post);
        entityManager.flush();

        Iterable<Post> foundPosts = postRepository.findByTitleContaining(title);

        assertThat(foundPosts).isNotEmpty();
        assertThat(foundPosts.iterator().next().getTitle()).isEqualTo(title);
    }
}
