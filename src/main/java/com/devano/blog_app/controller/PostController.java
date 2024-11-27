package com.devano.blog_app.controller;

import com.devano.blog_app.entity.Post;
import com.devano.blog_app.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {
    PostService postService;

    public List<Post> getPosts() {
        return List.of(new Post());
    }
}
