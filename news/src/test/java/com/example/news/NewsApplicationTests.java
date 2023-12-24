package com.example.news;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class NewsApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private NewNewsConroller newsController;
	@MockBean
	private PostRepository postRepository;

	@Test
	public void newsPostDeleteTest() {
		Post news = new Post();
		news.setId(1L);
		news.setTitle("Test news");
		news.setFull_text("This is a test news post");

		when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(news));

		String viewName = newsController.newsPostDelete(1L, null);

		assertEquals("redirect:/news", viewName);
	}
	@Test
	public void newsPostUpdate_ShouldUpdatePostAndRedirect() throws Exception {
		// Arrange
		Long id = 1L;
		String title = "testTitle";
		String anons = "testAnons";
		String full_text = "testFullText";

		Post post = new Post();
		post.setId(id);
		post.setTitle(title);
		post.setAnons(anons);
		post.setFull_text(full_text);

		when(postRepository.findById(id)).thenReturn(java.util.Optional.of(post));
		when(postRepository.save(any(Post.class))).thenReturn(post);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/news/{id}/edit", id)
						.param("title", title)
						.param("anons", anons)
						.param("full_text", full_text))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/news"));
	}
	@Test
	public void newsPostAdd_ShouldAddPostAndRedirect() throws Exception {
		// Arrange
		String title = "testTitle";
		String anons = "testAnons";
		String full_text = "testFullText";

		Post post = new Post(title, anons, full_text);

		when(postRepository.save(any(Post.class))).thenReturn(post);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/news/add")
						.param("title", title)
						.param("anons", anons)
						.param("full_text", full_text))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/news"));
	}
	@Test
	public void search_ShouldReturnSearchResults() throws Exception {
		// Arrange
		String title = "testTitle";
		Post post = new Post("title", "anons", "full_text");

		when(postRepository.findByTitleContaining(anyString())).thenReturn(Collections.singletonList(post));

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/search")
						.param("title", title))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("blog-details"))
				.andExpect(MockMvcResultMatchers.model().attribute("posts", Collections.singletonList(post)));
	}


}
