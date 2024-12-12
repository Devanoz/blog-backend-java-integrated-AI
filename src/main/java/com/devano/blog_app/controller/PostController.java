package com.devano.blog_app.controller;

import com.devano.blog_app.entity.Post;
import com.devano.blog_app.request.post.CreateGeminiPostRequest;
import com.devano.blog_app.request.post.CreatePostRequest;
import com.devano.blog_app.response.post.CreatePostResponse;
import com.devano.blog_app.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping
    public List<CreatePostResponse> getPosts() {
        return postService.getPosts();
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest post) {
        CreatePostResponse postResponse = postService.createPost(post);
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping("/ai/gemini")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreateGeminiPostRequest post) {
        var response = postService.createPostUsingGemini(post);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<CreatePostResponse> getPostBySlug(@PathVariable("slug") String slug) {
        CreatePostResponse postBySlug = postService.getPostBySlug(slug);
        return ResponseEntity.ok(postBySlug);
    }

    @PutMapping("/{id}")
    public Post editPostById(@PathVariable("id") Integer postId, @RequestBody Post sentPostByUser) {
        return postService.updatePostById(postId,sentPostByUser);
    }

    @DeleteMapping("/{id}")
    public Boolean deletePostBySlug(@PathVariable Integer id) {
        return postService.deletePostById(id);
    }

    @PostMapping("/{id}/publish")
    public Boolean publishPost(@PathVariable("id")Integer postId) {
        return postService.publishPost(postId);
    }

}
