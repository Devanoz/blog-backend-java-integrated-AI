package com.devano.blog_app.service;

import com.devano.blog_app.entity.Post;
import com.devano.blog_app.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    PostRepository postRepository;

    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPostBySlug(String slug) {
        return postRepository.findFirstBySlug(slug).orElse(null);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePostById(Integer postId, Post sentPostByUser) {
        Optional<Post> retreivedPost = postRepository.findById(postId);
        if(retreivedPost.isPresent()) {
            sentPostByUser.setSlug(sentPostByUser.getSlug());
            sentPostByUser.setTitle(sentPostByUser.getTitle());
            return retreivedPost.get();
        }
        return null;
    }

    public Boolean deletePostById(Integer id) {
        Post savedPost = postRepository.findById(id).orElse(null);
        if(savedPost == null) {
            return false;
        }
        savedPost.setDeleted(true);
        postRepository.save(savedPost);
        return true;
    }

    public Boolean publishPost(Integer id) {
        Optional<Post> savedPostById = postRepository.findById(id);
        if(savedPostById.isPresent()) {
            Post post = savedPostById.get();
            post.setPublished(true);
            postRepository.save(post);
            return true;
        }
        return false;
    }
}
