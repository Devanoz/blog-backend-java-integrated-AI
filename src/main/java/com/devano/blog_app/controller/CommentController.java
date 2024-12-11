package com.devano.blog_app.controller;

import com.devano.blog_app.entity.Comment;
import com.devano.blog_app.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private  CommentService commentService;

    @GetMapping("")
    public Object getComments(@RequestParam(value = "post_slug", required = false) String postSlug,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "limit", required = false,defaultValue = "10") Integer limit) {
        return commentService.getComments(postSlug,page,limit);
    }

    @PostMapping("")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @GetMapping("/{id}")
    public Object getComment(@PathVariable("id") Integer id) {
        return commentService.getComment(id);
    }

    @DeleteMapping("/{id}")
    public Object deleteComment(@PathVariable("id") Integer commentId) {
        return commentService.deleteComment(commentId);
    }

}