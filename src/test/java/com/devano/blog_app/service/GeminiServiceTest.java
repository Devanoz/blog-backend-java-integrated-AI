package com.devano.blog_app.service;

import com.devano.blog_app.request.post.CreateGeminiPostRequest;
import com.devano.blog_app.response.GeminiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.UUID;

@SpringBootTest
public class GeminiServiceTest {
    @Autowired
    private GeminiService geminiService;
    @Autowired
    private PostService postService;

    @Test
    public void given_text_to_generate_response_success() {
        String postTitle = "ronaldo";
        ResponseEntity<GeminiResponse> response = geminiService.generatePost(postTitle);
        String article = Objects.requireNonNull(response.getBody()).getCandidates().getFirst().getContent().getParts().getFirst().getText();
        Assertions.assertFalse(article.isBlank());
    }

    @Test
    public void x() {
        String postTitle = "ronaldo";
        String postSlug = "ronaldo %s".formatted(UUID.randomUUID());
        var response = postService.createPostUsingGemini(CreateGeminiPostRequest.builder().title(postTitle).slug(postSlug).build());
    }
}
