package com.devano.blog_app.service;

import com.devano.blog_app.entity.Comment;
import com.devano.blog_app.entity.Post;
import com.devano.blog_app.repository.jpa.CommentRepository;
import com.devano.blog_app.repository.jpa.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public Page<Comment> getComments(
            String postSlug,
            Integer page,
            Integer limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        return commentRepository.findAllByPost_Slug(postSlug, pageable);
    }


    public Comment createComment(Comment comment) {
        Optional<Post> postOptional = postRepository.findFirstBySlug(comment.getPost().getSlug());
        if(postOptional.isEmpty()) {
            return null;
        }
        Post post = postOptional.get();
        comment.setCreatedAt(Instant.now().getEpochSecond());
        comment.setPost(post);
        post.setCommentCount(post.getCommentCount() + 1);

        return commentRepository.save(comment);
    }

    public Comment getComment(Integer commentId) {
        Optional<Comment> commentById = commentRepository.findById(commentId);
        return commentById.orElse(null);
    }

    public Boolean deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }
}
