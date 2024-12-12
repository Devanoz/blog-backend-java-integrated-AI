package com.devano.blog_app.service;

import com.devano.blog_app.entity.Post;
import com.devano.blog_app.exception.ApiException;
import com.devano.blog_app.mapper.PostMapper;
import com.devano.blog_app.repository.jpa.PostRepository;
import com.devano.blog_app.request.post.CreateGeminiPostRequest;
import com.devano.blog_app.request.post.CreatePostRequest;
import com.devano.blog_app.response.GeminiResponse;
import com.devano.blog_app.response.post.CreatePostResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    PostRepository postRepository;
    private GeminiService geminiService;
    private final PostMapper postmapper = PostMapper.mapper;

    @Cacheable(cacheNames = "post", key = "'post-key'")
    public List<CreatePostResponse> getPosts() {
        List<Post> posts = postRepository.findAllByIsPublishedAndIsDeleted(true, false);
        return postmapper.mapToCreatePostResponse(posts);
    }

    public CreatePostResponse getPostBySlug(String slug) {
        Post post = postRepository.findFirstBySlug(slug).orElseThrow(() -> new ApiException("Not found", HttpStatus.NOT_FOUND));
        return postmapper.mapPostToCreatePostResponse(post);
    }

    public CreatePostResponse createPost(CreatePostRequest post) {
        Post postEntity = postmapper.mapToPostEntity(post);
        Post savedPost = postRepository.save(postEntity);
        return postmapper.mapPostToCreatePostResponse(savedPost);
    }

    @CircuitBreaker(name = "gemini", fallbackMethod = "fallback")
    public CreatePostResponse createPostUsingGemini(CreateGeminiPostRequest request) {

        ResponseEntity<GeminiResponse> response = geminiService.generatePost(request.getTitle());
        String article = Objects.requireNonNull(response.getBody()).getCandidates().getFirst().getContent().getParts().getFirst().getText().translateEscapes();
        String newArticle = article.translateEscapes();
        Post post = postRepository.save(Post.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .body(newArticle)
                .commentCount(0)
                .isDeleted(false)
                .isPublished(true)
                .build()
        );
        return postmapper.mapPostToCreatePostResponse(post);
    }

    //fallback method when #createPostUsingGeminiThrownException
    private CreatePostResponse fallback(CreateGeminiPostRequest request, Exception e) {
        return CreatePostResponse.builder()
                .id(0L)
                .body("Gemini service is not available now")
                .build();
    }

    public Post updatePostById(Integer postId, Post sentPostByUser) {
        Optional<Post> retreivedPost = postRepository.findById(postId);
        if (retreivedPost.isEmpty()) {
            throw new ApiException("Post not found", HttpStatus.NOT_FOUND);
        }
        sentPostByUser.setSlug(sentPostByUser.getSlug());
        sentPostByUser.setTitle(sentPostByUser.getTitle());
        return retreivedPost.get();
    }

    public Boolean deletePostById(Integer id) {
        Post savedPost = postRepository.findById(id).orElse(null);
        if (savedPost == null) {
            throw new ApiException("Post not found",HttpStatus.NOT_FOUND);
        }
        savedPost.setIsDeleted(true);
        postRepository.save(savedPost);
       return true;
    }

    @CacheEvict(cacheNames = "post", key = "'post-key'")
    public Boolean publishPost(Integer id) {
        Optional<Post> savedPostById = postRepository.findById(id);
        if (savedPostById.isEmpty()) {
            throw new ApiException("post not found", HttpStatus.NOT_FOUND);
        }
        Post post = savedPostById.get();
        post.setIsPublished(true);
        post.setPublishedAt(Instant.now().toEpochMilli());
        postRepository.save(post);
        return true;
    }
}
