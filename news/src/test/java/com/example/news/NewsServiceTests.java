package com.example.news;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsServiceTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void getAllNews_ShouldReturnAllNews() throws Exception {
        Post post = new Post("title", "anons", "full_text");
        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        mockMvc.perform(MockMvcRequestBuilders.get("/news"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("news-main"))
                .andExpect(MockMvcResultMatchers.model().attribute("posts", Collections.singletonList(post)));
    }
    @Test
    public void saveNews_ShouldReturnNews() {
        // given
        Post news = new Post( "Test anons", "Test title", "Test full text");
        when(postRepository.save(news)).thenReturn(news);

        // when
        Post savedNews = postRepository.save(news);

        // then
        assertNotNull(savedNews);
        assertEquals(news.getId(), savedNews.getId());
        assertEquals(news.getTitle(), savedNews.getTitle());
        assertEquals(news.getAnons(), savedNews.getAnons());
        assertEquals(news.getFull_text(), savedNews.getFull_text());
    }
    @Test
    public void deleteNewsById_ShouldDeleteNews() {
        // given
        Long id = 1L;

        // when
        postRepository.deleteById(id);

        // then
        verify(postRepository).deleteById(id);
        verifyNoMoreInteractions(postRepository);
    }
    @Test
    public void searchByTitle_ShouldReturnMatchingPosts() {
        String title = "Title";
        Iterable<Post> posts = Arrays.asList(
                new Post("title", "Title1", "Author1"),
                new Post("title", "Title2", "Author2")
        );

        when(postRepository.findByTitleContaining(title)).thenReturn(posts);
        Iterable<Post> foundPosts = postRepository.findByTitleContaining(title);

        assertEquals(posts, foundPosts);
    }


}
