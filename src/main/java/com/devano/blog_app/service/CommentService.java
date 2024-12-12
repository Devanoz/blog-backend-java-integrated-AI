package com.devano.blog_app.service;

import com.devano.blog_app.entity.Comment;
import com.devano.blog_app.entity.Post;
import com.devano.blog_app.exception.ApiException;
import com.devano.blog_app.mapper.CommentMapper;
import com.devano.blog_app.repository.jpa.CommentRepository;
import com.devano.blog_app.repository.jpa.PostRepository;
import com.devano.blog_app.request.comment.CreateCommentRequest;
import com.devano.blog_app.response.comment.CreateCommentResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private final CommentMapper commentMapper = CommentMapper.mapper;

    public Page<Comment> getComments(
            String postSlug,
            Integer page,
            Integer limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        return commentRepository.findAllByPost_Slug(postSlug, pageable);
    }

    @Transactional
    public CreateCommentResponse createComment(Integer postId, CreateCommentRequest comment) {
        Optional<Post> postById = postRepository.findById(postId);
        if(postById.isEmpty()) throw new ApiException("post not found", HttpStatus.NOT_FOUND);
        Post post = postById.get();
        post.setCommentCount(post.getCommentCount() + 1);
        Comment commentEntity = Comment.builder()
                .postId(Math.toIntExact(post.getId()))
                .name(comment.getName())
                .email(comment.getEmail())
                .post(post)
                .body(comment.getBody()).build();
        Comment savedComment = commentRepository.save(commentEntity);
        return commentMapper.commentToCreateCommentResponse(savedComment);
    }

    public CreateCommentResponse getComment(Integer commentId) {
        Optional<Comment> commentById = commentRepository.findById(commentId);
        if(commentById.isEmpty()) throw new ApiException("comment not found",HttpStatus.NOT_FOUND);
        return commentMapper.commentToCreateCommentResponse(commentById.get());
    }

    public Boolean deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }
}
