package com.devano.blog_app.post;

import com.devano.blog_app.entity.Post;
import com.devano.blog_app.repository.jpa.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class PostControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        // Build MockMvc using WebApplicationContext
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("positive path: get list of post should return list of post dto")
    public void get_post_should_return_list_of_post () throws Exception {
        postRepository.save(Post.builder()
                .title("memasak nasi")
                .body("memasak nasi itu menyenangkan")
                .slug("slug 1")
                .isPublished(true)
                .isDeleted(false)
                .commentCount(0)
                .build()
        );
        mockMvc.perform(get("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(result ->{
            int status = result.getResponse().getStatus();
            assertEquals(200,status);
            String contentAsString = result.getResponse().getContentAsString();
            assertTrue(contentAsString.contains("memasak nasi"));
        });
    }
}
